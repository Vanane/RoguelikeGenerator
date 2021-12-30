import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.pluginLoader.PluginDescriptor;
import com.pitchounous.pluginLoader.PluginLoader;
import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.ui.BasicUI;
import com.pitchounous.roguelike.ui.GameWindow;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.WorldBuilder;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Main {

    PluginLoader pl;
    Set<Class<?>> pluginCreatures;
    Set<Class<?>> pluginTiles;
    List<Class<?>> pluginUIClass;
    final Class<?> DEFAULT_UI_CLASS = GameWindow.class;

    final int mapWidth = 60;
    final int mapHeight = 60;

    public static void main(String[] args) {
        // Not really elegant but ui may be initilized differently
        // so we add the more freedom available
        new Main(80, 60);
    }

    public Main(int screenWidth, int screenHeight) {
        // Load config variables from plugins.json
        loadPlugins();

        Player player = new Player(10, 10);
        World world = createWorld(player);
        BasicUI ui = selectUI(world);

        ui.start();
    }

    private BasicUI selectUI(World world){
        BasicUI ui = null;
        Class<?> uiClass = null;

        if (pluginUIClass.size() > 0) {
            // Random choice / Update here to choose
            uiClass = pluginUIClass.get(0);
        }
        Object[] parameters = {world};
        if(uiClass != null){
            ui = (BasicUI) pl.instanciatePluginClass(uiClass, parameters);
        }else{
            ui = (BasicUI) pl.instanciatePluginClass(DEFAULT_UI_CLASS, parameters);
        }

        return ui;
    }

    private World createWorld(Player player) {
        return new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, player)
                .fillWithWall()
                .createRandomWalkCave(12232, 10, 10, 6000)
                .populateWorld(10)
                .build();
    }

    private void loadPlugins() {
        // Load config variables from plugins.json
        pl = PluginLoader.getInstance();

        // can add some choice here to filter tiles / creatures / ui / ...
        List<PluginDescriptor> additionalTiles = pl.getPluginDescriptors(Tile.class);
        pluginTiles = new HashSet<>();
        for (PluginDescriptor pd : additionalTiles) {
            pluginTiles.add(pl.getPluginDescriptorClass(pd));
        }

        List<PluginDescriptor> additionalCreatures = pl.getPluginDescriptors(Creature.class);
        pluginCreatures = new HashSet<>();
        for (PluginDescriptor pd : additionalCreatures) {
            pluginCreatures.add(pl.getPluginDescriptorClass(pd));
        }

        List<PluginDescriptor> pluginUIs = pl.getPluginDescriptors(BasicUI.class);
        pluginUIClass = new ArrayList<>();
        for (PluginDescriptor pd : pluginUIs) {
            pluginUIClass.add(pl.getPluginDescriptorClass(pd));
        }
    }

}
