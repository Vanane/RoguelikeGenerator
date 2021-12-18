package com.pitchounous.roguelike.world.tiles;

import java.util.HashMap;
import java.util.HashMap;

import com.pitchounous.roguelike.entities.Creature;

public class Ground extends Tile {

    public Ground(Integer xPos, Integer yPos) {
        super("ground", "brown", xPos, yPos, "black", true);
    }

    public char getGlyph() {
		return '.';
	}

    @Override
    public void onFootOn(Creature c) {
        // Simple dirt ground, nothing to see here
    }
}
