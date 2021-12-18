package com.pitchounous.roguelike.entities.creatures;

public class Player extends Creature {

    public Player(int xPos, int yPos) {
        super("player", "yellow", 10, 10, xPos, yPos);
    }
}
