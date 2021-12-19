package com.pitchounous.roguelike.ui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.pitchounous.roguelike.world.World;

public class Window extends Frame {
    GamePanel gp;
    World world;

    public Window(World world) {
        this.world = world;

        setTitle("Test");
        setIgnoreRepaint(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //dispose();
                System.exit(0);
            }
        });
    }

    public void addNotify() {
        super.addNotify();

        gp = new GamePanel(1280, 720, world);
        add(gp);
        // setContentPane(gp);
    }

}