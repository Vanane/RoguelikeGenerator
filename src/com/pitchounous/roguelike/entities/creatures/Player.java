package com.pitchounous.roguelike.entities.creatures;

import java.awt.Color;

public class Player extends Creature {

    public Player(int xPos, int yPos) {
        super("player", Color.YELLOW, 10, 10, xPos, yPos);
    }
}
