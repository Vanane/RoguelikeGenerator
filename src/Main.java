import com.pitchounous.pluginLoader.PluginLoader;
import com.pitchounous.roguelike.Roguelike;

public class Main {

    public static void main(String[] args) {
        // Load config variables from config.yaml
        // Load plugins from plugins.yaml
        // Start GUI thread
        PluginLoader.getInstance();
        Roguelike game = new Roguelike(80, 24);
        game.run();
    }
}
