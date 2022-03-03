package com.pitchounous;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class PluginSelectorUI {

    Frame mainFrame;

    List<DescriptorCategory> categories;

    public boolean isOpen;

    /**
     * Simple ui to make the user select his favorite plugins to load
     * 
     * @param categories
     */
    public PluginSelectorUI(List<DescriptorCategory> categories) {
        this.categories = categories;

        mainFrame = new Frame("Plugin Loader Window");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(10, 5));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                hide();
            }
        });

        this.addCheckBoxForCategories();

        Button exit = new Button("Start the game");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        mainFrame.add(exit);
        mainFrame.setVisible(true);
    }

    /**
     * For each plugin categories we create dedicated checkboxes
     */
    private void addCheckBoxForCategories() {
        CheckboxGroup checkboxGroup;
        boolean selectedByDefault;
        for (DescriptorCategory dc : this.categories) {
            mainFrame.add(new Label(dc.baseClass.getSimpleName() + " plugins"));

            checkboxGroup = (dc.atLeastOneRequired) ? new CheckboxGroup() : null;
            for (int i = 0; i < dc.descriptors.size(); i++) {
                PluginDescriptor pd = dc.descriptors.get(i);

                // Ensure first element is selected if at least one is required
                selectedByDefault = (dc.atLeastOneRequired && i == 0);
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

    public void showWindowDemo() {
        isOpen = true;
        mainFrame.setVisible(true);
    }

    public void hide() {
        isOpen = false;
        //mainFrame.setVisible(false);
    }

    /**
     * We return selected plugins
     * 
     * @param baseClass
     * @return
     */
    public List<PluginDescriptor> getSelectedPluginForBaseClass(Class<?> baseClass) {
        DescriptorCategory dc = null;
        for (DescriptorCategory cat : this.categories) {
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
}
