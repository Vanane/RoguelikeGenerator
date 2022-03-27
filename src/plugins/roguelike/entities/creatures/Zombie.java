package plugins.roguelike.entities.creatures;

import java.awt.Color;

public class Zombie extends Creature {

    /**
     * Basic zombie, attack sometimes, sleep sometimes ...
     *
     * @param xPos
     * @param yPos
     */
    public Zombie(int xPos, int yPos) {
        super("zombie", Color.GREEN, 20, 5, xPos, yPos);
    }
}
