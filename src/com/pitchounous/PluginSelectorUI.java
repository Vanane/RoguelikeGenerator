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

import javax.swing.JPanel;

public class PluginSelectorUI {

	Frame mainFrame;

	List<DescriptorCategory> checkboxCategories;
	HashMap<PluginDescriptor, DescriptorCategory> choiceCategories;

	public boolean isOpen;

	/**
	 * Simple ui to make the user select his favorite plugins to load
	 *
	 * @param chkCat
	 */
	public PluginSelectorUI(List<DescriptorCategory> chkCat, HashMap<PluginDescriptor, DescriptorCategory> choiceCat) {
		this.checkboxCategories = chkCat;
		this.choiceCategories = choiceCat;

		mainFrame = new Frame("Plugin Loader Window");
		mainFrame.setSize(600, 600);
		mainFrame.setLayout(new GridLayout(10, 5));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				hide();
			}
		});

		this.addCheckBoxForCategories();
		this.addComboBoxForBehaviours();

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
		for (DescriptorCategory dc : this.checkboxCategories) {		
			
			JPanel panel = new JPanel();
			panel.add(new Label(dc.baseClass.getSimpleName() + " plugins"));
			
			
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
				panel.add(tilCB);
			}
			mainFrame.add(panel);
		}
		
	}

	private void addComboBoxForBehaviours() {
		/*
		 * TODO : Pour chaque creature : Creer une categorie Creer un Combobox
		 * avec en choix les PluginDescriptor Il faut proposer que les pluginDescriptors
		 * compatibles avec la creature
		 */
		for (PluginDescriptor key : this.choiceCategories.keySet()) {
			System.out.println("Drawing behaviour for " + key.getPluginName());
			mainFrame.add(new Label("Behaviour for " + key.getPluginName() + " :"));

			DescriptorCategory dc = this.choiceCategories.get(key);

			// Creer la combobox qui va bien
			Choice comboBox = new Choice();
			comboBox.addItemListener(new ItemListener() {
				int lastIndex = -1;

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (lastIndex > -1) {
							dc.unselectPluginDescriptor(dc.descriptors.get(lastIndex));
						}
						dc.selectPluginDescriptor(dc.descriptors.get(comboBox.getSelectedIndex()));
						lastIndex = comboBox.getSelectedIndex();
					}
				}
			});

			// Pour chaque Behaviour de la creature, on l'ajoute e la combobox
			for (PluginDescriptor pd : dc.descriptors) {
				System.out.println("    - For creature " + key.getPluginName() + ", plugin " + pd.getClassName());
				comboBox.add(pd.getPluginName());
			}

			// Select first element by default
			dc.selectPluginDescriptor(dc.descriptors.get(0));
			mainFrame.add((comboBox));
		}
	}

	public void showWindowDemo() {
		isOpen = true;
		mainFrame.setVisible(true);
	}

	public void hide() {
		isOpen = false;
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
	 * Retourne le Behaviour selectionne pour chaque creature
	 *
	 * @return
	 */
	public HashMap<PluginDescriptor, PluginDescriptor> getSelectedPluginsForBehaviours() {
		HashMap<PluginDescriptor, PluginDescriptor> plugins = new HashMap<>();

		for (PluginDescriptor pd : this.choiceCategories.keySet()) {
			Set<PluginDescriptor> descriptors = this.choiceCategories.get(pd).getSelectedDescriptors();
			PluginDescriptor selectedBehaviour = descriptors.iterator().next();
			plugins.put(pd, selectedBehaviour);
		}

		return plugins;
	}
}
