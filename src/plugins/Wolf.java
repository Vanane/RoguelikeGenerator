package plugins;

import java.awt.Color;

import com.pitchounous.roguelike.entities.creatures.Creature;

public class Wolf extends Creature {

    public Wolf(int xPos, int yPos) {
        super("wolf", Color.DARK_GRAY, 10, 10, xPos, yPos);
    }
}
