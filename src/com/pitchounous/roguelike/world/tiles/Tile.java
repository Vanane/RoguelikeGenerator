package com.pitchounous.roguelike.world.tiles;

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

    public abstract void onFootOn(Creature c);

    public Tile(String type, String colorString, int xPos, int yPos, String color, boolean crossable) {
        super(type, colorString, color, xPos, yPos);
        isCrossable = crossable;
    }
}