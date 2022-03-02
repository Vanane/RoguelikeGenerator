package plugins.roguelike.ui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import plugins.roguelike.world.World;

public abstract class BasicUI extends Frame {

    protected World world;
    protected KeyHandler kl;
    protected boolean isRunning;

    /**
     * Abstract class for UI
     * 
     * @param world
     */
    protected BasicUI(World world) {
        super("Roguelike | Pitchounous");
        this.world = world;

        kl = new KeyHandler();
        isRunning = false;

        addKeyListener(kl);
        setVisible(true);
        setResizable(false);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                // dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Main function responsible to start the interface
     */
    public abstract void start();

    protected abstract void processInput();
}
