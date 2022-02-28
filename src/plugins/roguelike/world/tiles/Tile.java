package plugins.roguelike.world.tiles;

import java.awt.Color;

import plugins.roguelike.entities.Entity;
import plugins.roguelike.entities.creatures.Creature;

public abstract class Tile extends Entity {

    private boolean isCrossable;

    /**
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
     * @return
     */
    public boolean isCrossable() {
        return isCrossable;
    }

    /**
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