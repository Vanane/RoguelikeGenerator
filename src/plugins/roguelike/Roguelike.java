package plugins.roguelike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.DescriptorCategory;
import com.pitchounous.PluginDescriptor;
import com.pitchounous.PluginLoader;
import com.pitchounous.PluginSelectorUI;

import plugins.roguelike.entities.behaviours.Behaviour;
import plugins.roguelike.entities.behaviours.DefaultBehaviour;
import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.entities.creatures.Sheep;
import plugins.roguelike.entities.creatures.Zombie;
import plugins.roguelike.patterns.Observable;
import plugins.roguelike.patterns.Observer;
import plugins.roguelike.ui.BasicUI;
import plugins.roguelike.world.World;
import plugins.roguelike.world.WorldBuilder;
import plugins.roguelike.world.tiles.Tile;

/**
 * Main plugin
 */
public class Roguelike implements Observer {

    PluginLoader pl;
    PluginSelectorUI pluginSelector;
    BasicUI gameUI;
    Set<Class<?>> pluginCreatures;
    Set<Class<?>> pluginTiles;

    World world;

    /**
     * Plugin Behaviour d�fini pour chaque cr�ature
     *
     * @T1 Type Creature
     * @T2 Type Behaviour
     */
    HashMap<Class<?>, Class<?>> pluginBehaviours;

    Class<?> pluginUIClass;

    final int mapWidth = 60;
    final int mapHeight = 60;

    /**
     * This main plugin starts the entire game
     * !! A UI plugin must be selected
     */
    public Roguelike() {
        // Load config variables from plugins.json
        this.pl = PluginLoader.getInstance();
        this.startWorld();
    }

    private void startWorld() {
        this.showPluginSelectorUI();
        world = createWorld();
        if (gameUI == null) {
            gameUI = buildUI(world);
            gameUI.start();
        } else
            gameUI.setWorld(world);

    }

    private void stopWorld() {
        world = null;
    }

    /**
     * Create a new window where the user can select between several plugins to load
     */
    private void showPluginSelectorUI() {
        // Load config variables from plugins.json
        List<DescriptorCategory> sortedDescriptors = new ArrayList<>();
        List<PluginDescriptor> pluginCreatures = this.pl.getPluginDescriptors(Creature.class);
        sortedDescriptors.add(
                new DescriptorCategory(Tile.class, this.pl.getPluginDescriptors(Tile.class), false));
        sortedDescriptors.add(
                new DescriptorCategory(Creature.class, pluginCreatures, false));
        sortedDescriptors.add(
                new DescriptorCategory(BasicUI.class, this.pl.getPluginDescriptors(BasicUI.class), true));

        // Pour chaque créature chargée, on liste les Behaviours compatibles,
        // Et on les ajoute à une liste pour l'écran de sélection des plugins.
        HashMap<PluginDescriptor, DescriptorCategory> choiceDescriptors = new HashMap<>();

        List<PluginDescriptor> behaviours = this.pl.getPluginDescriptors(Behaviour.class);
        HashMap<String, Object> defaultBehaviourAttributes = new HashMap<>() {
            {
                put("canUseThisBehaviour", new ArrayList<>());
            }
        };
        behaviours.add(this.pl.createPluginDescriptorFromClass(DefaultBehaviour.class, defaultBehaviourAttributes));

        List<PluginDescriptor> allCreatures = this.pl.getPluginDescriptors(Creature.class);
        allCreatures.add(pl.createPluginDescriptorFromClass(Sheep.class, null));
        allCreatures.add(pl.createPluginDescriptorFromClass(Zombie.class, null));
        for (PluginDescriptor creaturePd : this.pl.getPluginDescriptors(Creature.class)) {
            List<PluginDescriptor> compatibleBehaviours = new ArrayList<>();
            for (PluginDescriptor behaviour : behaviours) {
                ArrayList<String> behaviourCreatures = (ArrayList<String>) behaviour.getAttributes()
                        .get("canUseThisBehaviour");

                if (behaviourCreatures.size() > 0 && !behaviourCreatures.contains(creaturePd.getPluginName())) {
                    continue;
                } else {
                    compatibleBehaviours.add(behaviour);
                }
            }
            choiceDescriptors.put(
                    creaturePd, new DescriptorCategory(Behaviour.class, compatibleBehaviours, true));
        }

        // If pluginSelector is null, it usually means that this is the first time the
        // world is loaded
        // If it is not null, it means that the window is already opened. Therefore,
        // this method should only read the selected plugins, as it is forcibly a hot
        // reload
        if (pluginSelector == null) {
            // Open the windows and wait for the user to select his favorite plugins
            pluginSelector = new PluginSelectorUI(sortedDescriptors, choiceDescriptors);
            pluginSelector.addObserver(this);
            pluginSelector.showWindowDemo();
            while (pluginSelector.isOpen) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                }
            }
        }
        this.readAndLoadSelectedPlugins();
    }

    private void readAndLoadSelectedPlugins() {
        List<PluginDescriptor> tileDescriptors = pluginSelector.getSelectedPluginForBaseClass(Tile.class);
        List<PluginDescriptor> creatureDescriptors = pluginSelector.getSelectedPluginForBaseClass(Creature.class);
        List<PluginDescriptor> uiDescriptors = pluginSelector.getSelectedPluginForBaseClass(BasicUI.class);
        HashMap<PluginDescriptor, PluginDescriptor> behaviourDescriptors = pluginSelector.getSelectedPluginsForCombo();

        this.loadSelectedPlugins(tileDescriptors, creatureDescriptors, behaviourDescriptors, uiDescriptors.get(0));
    }

    /**
     * Load three different types of required plugins
     *
     * @param tileDescriptors
     * @param creatureDescriptors
     * @param behaviourDescriptors
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
        for (PluginDescriptor creature : behaviourDescriptors.keySet()) {
            this.pluginBehaviours.put(pl.getPluginDescriptorClass(creature),
                    pl.getPluginDescriptorClass(behaviourDescriptors.get(creature)));
        }

    }

    /**
     * Instantiate the game world
     *
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

    @Override
    public void onNotify(Observable source, String data) {
        if (source == pluginSelector) {
            System.out.println("Received message : " + data);
            this.stopWorld();
            this.startWorld();
        }
    }
}
