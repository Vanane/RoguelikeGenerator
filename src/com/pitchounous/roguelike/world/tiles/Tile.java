package com.pitchounous.roguelike.world.tiles;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashMap;

import com.pitchounous.roguelike.Roguelike;
import com.pitchounous.roguelike.entities.Creature;
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

    public void onStep(Creature c) {
        if (!this.isCrossable) {
            System.err.println("You tried to walk on " + this.getClass().getName() + " wich is not crossable !");
        }
        this.onFootOn(c);
    };

    public abstract void onFootOn(Creature c);

    public Tile(String type, String colorString, Integer xPos, Integer yPos, String color, boolean crossable) {
        super(type, colorString, xPos, yPos);
        backgroundColor = Roguelike.stringToColor(color);
        isCrossable = crossable;
    }
}