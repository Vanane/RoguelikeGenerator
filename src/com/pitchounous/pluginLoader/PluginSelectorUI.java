package com.pitchounous.pluginLoader;

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

public class PluginSelectorUI {

    Frame mainFrame;

    List<PluginDescriptor> selectedTiles;
    List<PluginDescriptor> selectedCreatures;
    List<PluginDescriptor> selectedUIs;

    public boolean isOpen;

    public PluginSelectorUI(
            List<PluginDescriptor> tileDescriptors,
            List<PluginDescriptor> creatureDescriptors,
            List<PluginDescriptor> uiDescriptors) {

        selectedTiles = new ArrayList<>();
        selectedUIs = new ArrayList<>();
        selectedCreatures = new ArrayList<>();

        mainFrame = new Frame("Plugin Loader Window");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                hide();
            }
        });

        // Limiting to only one choice for UI
        CheckboxGroup uiChoices = new CheckboxGroup();
        for (PluginDescriptor uiType : uiDescriptors) {
            mainFrame.add(this.createCB(uiType, false, uiChoices, selectedUIs));
        }

        for (PluginDescriptor tilePD : tileDescriptors) {
            mainFrame.add(this.createCB(tilePD, false, null, selectedTiles));
        }

        for (PluginDescriptor ceraturePD : creatureDescriptors) {
            mainFrame.add(this.createCB(ceraturePD, false, null, selectedCreatures));
        }

        Button exit = new Button("EXIT");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        mainFrame.add(exit);

        mainFrame.setVisible(true);
    }

    private Checkbox createCB(
            PluginDescriptor pd, Boolean isActive,
            CheckboxGroup checkboxGroup, List<PluginDescriptor> activeListToUpdate) {
        Checkbox tilCB = new Checkbox(pd.getPluginName(), isActive, checkboxGroup);
        tilCB.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                activeListToUpdate.add(pd);
            }
        });
        return tilCB;
    }

    public void showWindowDemo() {
        isOpen = true;
        mainFrame.setVisible(true);
    }

    public void hide() {
        isOpen = false;
        mainFrame.setVisible(false);
    }

    public List<PluginDescriptor> getUserSelectedTiles() {
        return this.selectedTiles;
    }

    public PluginDescriptor getUserSelectedUI() {
        // Return last selected UI
        return this.selectedUIs.get(this.selectedUIs.size()-1);
    }

    public List<PluginDescriptor> getUserSelectedCreatures() {
        return this.selectedCreatures;
    }
}
