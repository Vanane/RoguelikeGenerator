package plugins.roguelike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.DescriptorCategory;
import com.pitchounous.PluginDescriptor;
import com.pitchounous.PluginLoader;
import com.pitchounous.PluginSelectorUI;

import plugins.roguelike.entities.behaviours.Behaviour;
import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.ui.BasicUI;
import plugins.roguelike.world.World;
import plugins.roguelike.world.WorldBuilder;
import plugins.roguelike.world.tiles.Tile;

/**
 * Main plugin
 */
public class Roguelike {

    PluginLoader pl;
    Set<Class<?>> pluginCreatures;
    Set<Class<?>> pluginTiles;

    /** Plugin Behaviour défini pour chaque créature
     * @T1 Type Creature
     * @T2 Type Behaviour */
    HashMap<Class<?>, Class<?>> pluginBehaviours;

    Class<?> pluginUIClass;

    final int mapWidth = 60;
    final int mapHeight = 60;

    /**
     * This main plugin starts the entire game
     * !! A UI plugin must be selected
     *
     * @param screenWidth
     * @param screenHeight
     */
    public Roguelike(int screenWidth, int screenHeight) {
        // Load config variables from plugins.json
        this.pl = PluginLoader.getInstance();

        this.showPluginSelectorUI();

        World world = createWorld();
        BasicUI ui = buildUI(world);

        ui.start();
    }

    /**
     * Create a new window where the user can select between several plugins to load
     */
    private void showPluginSelectorUI() {
        // Load config variables from plugins.json
        List<DescriptorCategory> sortedDescriptors = new ArrayList<>();
        sortedDescriptors.add(
            new DescriptorCategory(Tile.class, this.pl.getPluginDescriptors(Tile.class), false));
        sortedDescriptors.add(
            new DescriptorCategory(Creature.class, this.pl.getPluginDescriptors(Creature.class), false));
        sortedDescriptors.add(
            new DescriptorCategory(BasicUI.class, this.pl.getPluginDescriptors(BasicUI.class), true));
            
         
        // Pour chaque créature chargée, on liste les Behaviours compatibles, 
        // Et on les ajoute à une liste pour l'écran de sélection des plugins.
        HashMap<PluginDescriptor, DescriptorCategory> choiceDescriptors = new HashMap<>();

        for(PluginDescriptor creaturePd : this.pl.getPluginDescriptors(Creature.class))
        {
            List<PluginDescriptor> behaviourDescriptors = new ArrayList<>(); // Liste des behaviours compatibles avec la créature courante
            for(PluginDescriptor behaviour : this.pl.getPluginDescriptors(Behaviour.class))
            {
                ArrayList<String> behaviourAttributes = (ArrayList<String>) behaviour.getAttributes().get("canUseThisBehaviour");

                if(behaviourAttributes.size() > 0 && !behaviourAttributes.contains(creaturePd.getPluginName()))
                    continue;
                else
                    behaviourDescriptors.add(behaviour);
            }
            choiceDescriptors.put(
                creaturePd, new DescriptorCategory(Behaviour.class, behaviourDescriptors, true));
        }

        // Open the windows and wait for the user to select is favorite plugins
        PluginSelectorUI ui = new PluginSelectorUI(sortedDescriptors, choiceDescriptors);
        ui.showWindowDemo();
        while (ui.isOpen) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
        }

        List<PluginDescriptor> tileDescriptors = ui.getSelectedPluginForBaseClass(Tile.class);
        List<PluginDescriptor> creatureDescriptors = ui.getSelectedPluginForBaseClass(Creature.class);
        List<PluginDescriptor> uiDescriptors = ui.getSelectedPluginForBaseClass(BasicUI.class);
        HashMap<PluginDescriptor, PluginDescriptor> behaviourDescriptors = ui.getSelectedPluginsForBehaviours();

        this.loadSelectedPlugins(tileDescriptors, creatureDescriptors, behaviourDescriptors, uiDescriptors.get(0));
    }

    /**
     * Load three different types of required plugins
     * 
     * @param tileDescriptors
     * @param creatureDescriptors
     * @param uiDescriptor
     */
    private void loadSelectedPlugins(
            List<PluginDescriptor> tileDescriptors,
            List<PluginDescriptor> creatureDescriptors,
            HashMap<PluginDescriptor, PluginDescriptor> behaviourDescriptors,
            PluginDescriptor uiDescriptor) {
        // can add some choice here to filter tiles / creatures / ui / ...
        this.pluginTiles = new HashSet<>();
        for (PluginDescriptor pd : tileDescriptors) {
            this.pluginTiles.add(pl.getPluginDescriptorClass(pd));
        }

        this.pluginCreatures = new HashSet<>();
        for (PluginDescriptor pd : creatureDescriptors) {
            this.pluginCreatures.add(pl.getPluginDescriptorClass(pd));
        }
        
        pluginUIClass = this.pl.getPluginDescriptorClass(uiDescriptor);

        this.pluginBehaviours = new HashMap<>();
        for(PluginDescriptor creature : behaviourDescriptors.keySet()) {
            this.pluginBehaviours.put(pl.getPluginDescriptorClass(creature), pl.getPluginDescriptorClass(behaviourDescriptors.get(creature)));
        }

    }

    /**
     * Instantiate the game world
     * 
     * @param player
     * @return
     */
    private World createWorld() {
        return new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, pluginBehaviours)
                .fillWithWall()
                .createRandomWalkCave(12232, 10, 10, 6000)
                .addPlayer(10, 10)
                .populateWorld(10)
                .build();
    }

    /**
     * Create the UI
     * 
     * @param world
     * @return
     */
    private BasicUI buildUI(World world) {
        Object[] parameters = { world };
        return (BasicUI) pl.instantiatePluginClass(pluginUIClass, parameters);
    }
}
