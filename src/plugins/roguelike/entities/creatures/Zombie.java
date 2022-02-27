package plugins.roguelike.entities.creatures;

import java.awt.Color;

public class Zombie extends Creature {
    /**
     * 
     * @param xPos
     * @param yPos
     */
    public Zombie(int xPos, int yPos) {
        super("zombie", Color.GREEN, 20, 5, xPos, yPos);
    }
}
