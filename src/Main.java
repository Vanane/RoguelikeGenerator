import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pitchounous.pluginLoader.PluginDescriptor;
import com.pitchounous.pluginLoader.PluginLoader;
import com.pitchounous.roguelike.Roguelike;
import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Main {

    public static void main(String[] args) {
        // Load config variables from plugins.json
        PluginLoader pl = PluginLoader.getInstance();

        List<PluginDescriptor> additionalTiles = pl.getPluginDescriptors(Tile.class);
        Set<Class<?>> pluginTiles = new HashSet<>();
        for (PluginDescriptor pd : additionalTiles) {
            pluginTiles.add(pl.getPluginDescriptorClass(pd));
        }

        List<PluginDescriptor> additionalCreatures = pl.getPluginDescriptors(Creature.class);
        Set<Class<?>> pluginCreatures = new HashSet<>();
        for (PluginDescriptor pd : additionalCreatures) {
            pluginCreatures.add(pl.getPluginDescriptorClass(pd));
        }

        new Roguelike(80, 60, pluginTiles, pluginCreatures);
    }
}
