package com.pitchounous.roguelike.world.tiles;

import java.awt.Color;

import com.pitchounous.roguelike.entities.creatures.Creature;


public abstract class ActivableTile extends Tile {

    protected boolean hasBeenActivated;

    public ActivableTile(String type, Color stringColor, int xPos, int yPos, Color backColor, boolean crossable) {
        super(type, stringColor, xPos, yPos, backColor, crossable);
        hasBeenActivated = false;
    }

    @Override
    protected void onFootOn(Creature c){
        if(!hasBeenActivated){
            this.activateEffect(c);
            hasBeenActivated = true;
        }
    }

    protected abstract void activateEffect(Creature c);

}
