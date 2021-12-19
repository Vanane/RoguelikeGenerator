package com.pitchounous.roguelike.entities.creatures;

import java.awt.Color;

public class Zombie extends Creature {

    public Zombie(int xPos, int yPos) {
        super("zombie", Color.GREEN, 20, 5, xPos, yPos);
    }
}
