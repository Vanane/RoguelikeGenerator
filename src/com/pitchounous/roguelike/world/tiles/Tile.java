package com.pitchounous.roguelike.world.tiles;

import java.awt.Color;

import com.pitchounous.roguelike.entities.Entity;
import com.pitchounous.roguelike.entities.creatures.Creature;

public abstract class Tile extends Entity {

    private boolean isCrossable;

    public boolean isCrossable() {
        return isCrossable;
    }

    public void onStep(Creature c) {
        if (!this.isCrossable) {
            System.err.println("You tried to walk on " + this.getClass().getName() + " wich is not crossable !");
        }
        this.onFootOn(c);
    };

    protected abstract void onFootOn(Creature c);

    public Tile(String type, Color stringColor, int xPos, int yPos, Color backColor, boolean crossable) {
        super(type, stringColor, backColor, xPos, yPos);
        isCrossable = crossable;
    }
}