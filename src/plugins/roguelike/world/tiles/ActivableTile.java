package plugins.roguelike.world.tiles;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;

public abstract class ActivableTile extends Tile {

    protected boolean hasBeenActivated;

    /**
     * 
     * @param type
     * @param stringColor
     * @param xPos
     * @param yPos
     * @param backColor
     * @param crossable
     */
    public ActivableTile(String type, Color stringColor, int xPos, int yPos, Color backColor, boolean crossable) {
        super(type, stringColor, xPos, yPos, backColor, crossable);
        hasBeenActivated = false;
    }

    @Override
    protected void onFootOn(Creature c) {
        if (!hasBeenActivated) {
            this.activateEffect(c);
            hasBeenActivated = true;
        }
    }

    protected abstract void activateEffect(Creature c);

}
