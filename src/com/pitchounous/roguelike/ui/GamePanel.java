package com.pitchounous.roguelike.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.KeyListener;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.World;

public class GamePanel extends Panel implements Runnable {

    int width;
    int height;
    Graphics2D g2D;

    KeyListener kl;
    Thread thread;
    boolean running;

    final int FRAMES_PER_SECOND = 60;
    final long TIME_PER_LOOP = 1000000000 / FRAMES_PER_SECOND;
    long startTime;
    long endTime;
    long sleepTime;

    World world;

    public GamePanel(int width, int height, World world) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.running = false;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.gray);
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;
        kl = new KeyHandler();
        addKeyListener(kl);
    }

    @Override
    public void run() {
        init();
        while (running) {

            startTime = System.nanoTime();
            input(kl);
            update();
            render();
            endTime = System.nanoTime();

            long sleepTime = TIME_PER_LOOP - (endTime - startTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1000000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void input(KeyListener kl2) {
    }

    public void update() {
        world.update();
    }

    @Override
    public void paint(Graphics g) {
        g2D = (Graphics2D) g;
        drawBackground(g);
        drawCreatures(g);
    }

    private void drawCreatures(Graphics g) {
        int creatureWidth = 5;
        int squareWidth = (int) (this.width / world.width);
        int squareHeight = (int) (this.height / 80);
        int xgap = (int) (squareWidth - creatureWidth) / 2;
        int ygap = (int) (squareHeight - creatureWidth) / 2;
        for (Creature c : world.getAliveCreatures()) {
            g.setColor(c.getColor());
            g.fillRect(
                    squareWidth * c.getX() + xgap,
                    squareHeight * c.getY() + ygap,
                    creatureWidth,
                    creatureWidth);
        }
    }

    private void drawBackground(Graphics g) {
        int squareWidth = (int) (width / world.width);
        int squareHeight = (int) (height / 80);
        for (int x = 0; x < world.width; x++) {
            for (int y = 0; y < world.height; y++) {
                g.setColor(world.getTile(x, y).getColor());
                g.fillRect(squareWidth * x, squareHeight * y, squareWidth, squareHeight);
            }
        }
    }

    public void render() {
        if (g2D != null) {
            drawBackground(g2D);
            drawCreatures(g2D);
        }
    }
}
