package plugins.roguelike.entities.creatures;

import java.awt.Color;

public class Sheep extends Creature {
    /**
     * 
     * @param xPos
     * @param yPos
     */
    public Sheep(int xPos, int yPos) {
        super("sheep", Color.WHITE, 10, 0, xPos, yPos);
    }
}
