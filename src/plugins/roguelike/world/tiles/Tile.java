package plugins.roguelike.world.tiles;

import java.awt.Color;

import plugins.roguelike.entities.Entity;
import plugins.roguelike.entities.creatures.Creature;

public abstract class Tile extends Entity {

    private boolean isCrossable;

    /**
     * Base abstract class to create world tiles
     * 
     * @param type
     * @param stringColor
     * @param xPos
     * @param yPos
     * @param backColor
     * @param crossable
     */
    public Tile(String type, Color stringColor, int xPos, int yPos, Color backColor, boolean crossable) {
        super(type, stringColor, backColor, xPos, yPos);
        isCrossable = crossable;
    }

    /**
     * 
     * @return boolean
     */
    public boolean isCrossable() {
        return isCrossable;
    }

    /**
     * Transfer some behavior to the derived tile classes
     * 
     * @param c
     */
    public void onStep(Creature c) {
        if (!this.isCrossable) {
            System.err.println("You tried to walk on " + this.getClass().getName() + " which is not crossable !");
        }
        this.onFootOn(c);
    };

    protected abstract void onFootOn(Creature c);

}