package plugins;

import com.pitchounous.roguelike.entities.creatures.Creature;

public class Wolf extends Creature {

    public Wolf(int xPos, int yPos) {
        super("wolf", "grey", 10, 10, xPos, yPos);
    }
}
