package com.pitchounous.roguelike.world.tiles;

import java.awt.Color;
import java.util.Map;

import com.pitchounous.roguelike.Roguelike;
import com.pitchounous.roguelike.entities.Entity;

public abstract class Tile extends Entity {

    private Color backgroundColor;
    private boolean isCrossable;

    public boolean isCrossable() {
        return isCrossable;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void onStep() {
        if (!this.isCrossable) {
            System.err.println("You tried to walk on " + this.getClass().getName() + " wich is not crossable !");
        }
        this.onFootOn();
    };

    public abstract void onFootOn();

    public Tile(Map<String, String> tileData, int xpos, int ypos, String color, boolean crossable) {
        super(tileData, xpos, ypos);
        backgroundColor = Roguelike.stringToColor(color);
        isCrossable = crossable;
    }
}