package com.pitchounous.roguelike.ui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.pitchounous.roguelike.world.World;

public abstract class BasicUI extends Frame {

    protected World world;
    protected KeyHandler kl;
    protected boolean isRunning;

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

    // main function responsible to start the interface
    public abstract void start();

    protected abstract void processInput();
}
