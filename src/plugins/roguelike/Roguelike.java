package plugins.roguelike;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.PluginDescriptor;
import com.pitchounous.PluginLoader;
import com.pitchounous.PluginSelectorUI;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.entities.creatures.Player;
import plugins.roguelike.ui.BasicUI;
import plugins.roguelike.world.World;
import plugins.roguelike.world.WorldBuilder;
import plugins.roguelike.world.tiles.Tile;

public class Roguelike {

    PluginLoader pl;
    Set<Class<?>> pluginCreatures;
    Set<Class<?>> pluginTiles;
    Class<?> pluginUIClass;

    final int mapWidth = 60;
    final int mapHeight = 60;

    /**
     * 
     * @param screenWidth
     * @param screenHeight
     */
    public Roguelike(int screenWidth, int screenHeight) {
        System.out.println("Running default call");
        // Load config variables from plugins.json
        showPluginSelectorUI();

        Player player = new Player(10, 10);
        World world = createWorld(player);
        BasicUI ui = buildUI(world);

        ui.start();
    }

    private void showPluginSelectorUI() {
        // Load config variables from plugins.json
        pl = PluginLoader.getInstance();
        List<PluginDescriptor> tileDescriptors = pl.getPluginDescriptors(Tile.class);
        List<PluginDescriptor> creatureDescriptors = pl.getPluginDescriptors(Creature.class);
        List<PluginDescriptor> uiDescriptors = pl.getPluginDescriptors(BasicUI.class);

        // Open the windows and wait for the user to select is favorite plugins
        PluginSelectorUI psui = new PluginSelectorUI(tileDescriptors, creatureDescriptors, uiDescriptors);
        psui.showWindowDemo();

        while (psui.isOpen) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
        }
        tileDescriptors = psui.getUserSelectedTiles();
        creatureDescriptors = psui.getUserSelectedCreatures();
        PluginDescriptor uiDescriptor = psui.getUserSelectedUI();

        loadSelectedPlugins(tileDescriptors, creatureDescriptors, uiDescriptor);
    }

    private void loadSelectedPlugins(
            List<PluginDescriptor> tileDescriptors,
            List<PluginDescriptor> creatureDescriptors,
            PluginDescriptor uiDescriptor) {
        // can add some choice here to filter tiles / creatures / ui / ...
        pluginTiles = new HashSet<>();
        for (PluginDescriptor pd : tileDescriptors) {
            pluginTiles.add(pl.getPluginDescriptorClass(pd));
        }

        pluginCreatures = new HashSet<>();
        for (PluginDescriptor pd : creatureDescriptors) {
            pluginCreatures.add(pl.getPluginDescriptorClass(pd));
        }

        pluginUIClass = pl.getPluginDescriptorClass(uiDescriptor);
    }

    /**
     * 
     * @param player
     * @return
     */
    private World createWorld(Player player) {
        return new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, player)
                .fillWithWall()
                .createRandomWalkCave(12232, 10, 10, 6000)
                .populateWorld(10)
                .build();
    }

    /**
     * 
     * @param world
     * @return
     */
    private BasicUI buildUI(World world) {
        Object[] parameters = { world };
        return (BasicUI) pl.instanciatePluginClass(pluginUIClass, parameters);
    }
}
