package plugins.roguelike.entities.creatures;

import java.awt.Color;

import plugins.roguelike.entities.behaviours.EmptyBehaviour;

public class Player extends Creature {

    /**
     * Player object, little 'P' on the map
     *
     * @param xPos
     * @param yPos
     */
    public Player(int xPos, int yPos) {
        super("player", Color.YELLOW, 10, 10, xPos, yPos);
        this.behaviour = new EmptyBehaviour(this);
    }
}
