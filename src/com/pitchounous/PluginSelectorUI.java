package com.pitchounous;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import plugins.roguelike.patterns.Observable;
import plugins.roguelike.patterns.Observer;

public class PluginSelectorUI implements Observable {

    Frame mainFrame;
	private List<Observer> observers;

    List<DescriptorCategory> checkboxCategories;
    HashMap<PluginDescriptor, DescriptorCategory> comboBoxCategories;

    public boolean isStarted;
    public boolean gameLaunched;

    /**
     * Simple ui to make the user select his favorite plugins to load
     *
     * @param chkCat
     */
    public PluginSelectorUI(List<DescriptorCategory> chkCat, HashMap<PluginDescriptor, DescriptorCategory> choiceCat) {
        this.checkboxCategories = chkCat;
        isStarted = false;
        this.comboBoxCategories = choiceCat;

        mainFrame = new Frame("Plugin Loader Window");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(10, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                hide();
            }
        });

        this.addCheckBoxForDescriptorCategories();
        this.addComboBoxForDescriptorCategories();

        Button exit = new Button("Start the game");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hide();
                isStarted = true;
            }
        });


        Button reload = new Button("Reload");
        reload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(!isStarted) return;
            	notifyObject("Reload");
                hide();
            }
        });

        mainFrame.add(exit);
        mainFrame.add(reload);
        mainFrame.setVisible(true);
    }

    /**
     * For each plugin categories we create dedicated checkboxes
     */
    private void addCheckBoxForDescriptorCategories() {
        for (DescriptorCategory dc : this.checkboxCategories) {
            mainFrame.add(new Label(dc.baseClass.getSimpleName() + " plugins"));

            CheckboxGroup checkboxGroup = (dc.atLeastOneRequired) ? new CheckboxGroup() : null;
            for (int i = 0; i < dc.descriptors.size(); i++) {
                PluginDescriptor pd = dc.descriptors.get(i);

                // Ensure first element is selected if at least one is required
                boolean selectedByDefault = (dc.atLeastOneRequired && i == 0);
                Checkbox tilCB = new Checkbox(pd.getPluginName(), selectedByDefault, checkboxGroup);
                if (selectedByDefault) {
                    dc.selectPluginDescriptor(pd);
                }

                tilCB.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            dc.selectPluginDescriptor(pd);
                        } else {
                            dc.unselectPluginDescriptor(pd);
                        }
                    }
                });
                mainFrame.add(tilCB);
            }
        }
    }

    private void addComboBoxForDescriptorCategories() {
        for (PluginDescriptor key : this.comboBoxCategories.keySet()) {
            mainFrame.add(new Label(key.getPluginName() + " for " + key.getPluginName() + " :"));

            DescriptorCategory dc = this.comboBoxCategories.get(key);

            Choice comboBox = new Choice();
            comboBox.addItemListener(new ItemListener() {
                int lastIndex = -1;

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        if (lastIndex > -1) {
                            dc.unselectPluginDescriptor(dc.descriptors.get(lastIndex));
                        }
                        PluginDescriptor pd = dc.descriptors.get(comboBox.getSelectedIndex());
                        dc.selectPluginDescriptor(pd);
                        lastIndex = comboBox.getSelectedIndex();
                        System.out.println("Setting " + pd.getClassName() + " for " + key.getPluginName());
                    }
                }
            });

            for (PluginDescriptor pd : dc.descriptors) {
                comboBox.add(pd.getPluginName());
            }

            // Select first element by default
            PluginDescriptor pd = dc.descriptors.get(0);
            dc.selectPluginDescriptor(pd);
            System.out.println("Setting " + pd.getClassName() + " for " + key.getPluginName());

            mainFrame.add(comboBox);
        }
    }

    public void showWindowDemo() {
        gameLaunched = true;
        mainFrame.setVisible(true);
    }

    public void hide() {
        gameLaunched = false;
        // mainFrame.setVisible(false);
    }

    /**
     * We return selected plugins
     *
     * @param baseClass
     * @return
     */
    public List<PluginDescriptor> getSelectedPluginForBaseClass(Class<?> baseClass) {
        DescriptorCategory dc = null;
        for (DescriptorCategory cat : this.checkboxCategories) {
            if (cat.baseClass == baseClass) {
                dc = cat;
                break;
            }
        }

        List<PluginDescriptor> selectedDescriptors = new ArrayList<>();
        if (dc != null) {
            selectedDescriptors.addAll(dc.getSelectedDescriptors());
        }
        return selectedDescriptors;
    }

    /**
     *
     * @return
     */
    public HashMap<PluginDescriptor, PluginDescriptor> getSelectedPluginsForCombo() {
        HashMap<PluginDescriptor, PluginDescriptor> plugins = new HashMap<>();

        for (PluginDescriptor pd : this.comboBoxCategories.keySet()) {
            Set<PluginDescriptor> descriptors = this.comboBoxCategories.get(pd).getSelectedDescriptors();
            // Getting the first element as only one can be selected
            plugins.put(pd, descriptors.iterator().next());
        }

        return plugins;
    }



	@Override
	public void notifyObject(String data) {
		for(Observer observer : observers)
			observer.onNotify(this,  data);

	}

	@Override
	public void addObserver(Observer o) {
		if(observers == null) observers = new ArrayList<Observer>();
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}
}
