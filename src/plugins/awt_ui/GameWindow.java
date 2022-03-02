package plugins.awt_ui;

import plugins.roguelike.ui.BasicUI;
import plugins.roguelike.world.World;

public class GameWindow extends BasicUI {
    GamePanel gp;

    /**
     * Full AWT ui
     * 
     * @param world
     */
    public GameWindow(World world) {
        super(world);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Dedicated responsibility to awt GamePanel
     */
    public void addNotify() {
        super.addNotify();

        gp = new GamePanel(720, 720, world, kl, isRunning);
        add(gp);
        // setContentPane(gp);
    }

    @Override
    public void start() {
        // Nothing done here as the window is already displayed
        // in the addNotify hook
    }

    @Override
    public void processInput() {
        // GamePanel is processing input instead
    }

}