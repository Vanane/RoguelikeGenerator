import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.pluginLoader.PluginDescriptor;
import com.pitchounous.pluginLoader.PluginLoader;
import com.pitchounous.pluginLoader.PluginSelectorUI;
import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.ui.BasicUI;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.WorldBuilder;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Main {

    PluginLoader pl;
    Set<Class<?>> pluginCreatures;
    Set<Class<?>> pluginTiles;
    Class<?> pluginUIClass;

    final int mapWidth = 60;
    final int mapHeight = 60;

    public static void main(String[] args) {
        new Main(80, 60);
    }

    public Main(int screenWidth, int screenHeight) {
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

    private World createWorld(Player player) {
        return new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, player)
                .fillWithWall()
                .createRandomWalkCave(12232, 10, 10, 6000)
                .populateWorld(10)
                .build();
    }

    private BasicUI buildUI(World world) {
        Object[] parameters = { world };
        return (BasicUI) pl.instanciatePluginClass(pluginUIClass, parameters);
    }
}
