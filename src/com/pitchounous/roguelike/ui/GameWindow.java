package com.pitchounous.roguelike.ui;

import com.pitchounous.roguelike.world.World;

public class GameWindow extends BasicUI {
    GamePanel gp;

    public GameWindow(World world) {
        super(world);

        pack();
        setLocationRelativeTo(null);
    }

    public void addNotify() {
        super.addNotify();

        gp = new GamePanel(720, 720, world, kl, isRunning);
        add(gp);
        // setContentPane(gp);
    }

    @Override
    public void start() {
        // Nothing done here as the window is alredy displayed
        // in the addNotify hook
    }

    @Override
    public void processInput() {
        // GamePanel is processing input instead
    }

}