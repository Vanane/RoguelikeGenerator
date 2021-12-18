package com.pitchounous.roguelike.world.tiles;

import com.pitchounous.roguelike.entities.creatures.Creature;

public class Ground extends Tile {

    public Ground(int xPos, int yPos) {
        super("ground", "brown", xPos, yPos, "black", true);
    }

    public char getGlyph() {
        return ' ';
    }

    @Override
    public void onFootOn(Creature c) {
        // Simple dirt ground, nothing to see here
    }
}
