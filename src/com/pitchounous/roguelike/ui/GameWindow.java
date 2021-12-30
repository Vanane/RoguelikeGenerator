package com.pitchounous.roguelike.ui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.pitchounous.roguelike.world.World;

public class GameWindow extends Frame implements BasicUI{
    GamePanel gp;
    World world;

    public GameWindow(World world) {
        this.world = world;

        setTitle("Pitchounous RogueLike 2k22");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                // dispose();
                System.exit(0);
            }
        });
    }

    public void addNotify() {
        super.addNotify();

        gp = new GamePanel(720, 720, world);
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