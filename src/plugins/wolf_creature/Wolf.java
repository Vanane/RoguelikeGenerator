package plugins.wolf_creature;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;

public class Wolf extends Creature {

    public Wolf(int xPos, int yPos) {
        super("wolf", Color.DARK_GRAY, 10, 10, xPos, yPos);
    }
}
