package com.pitchounous.roguelike.world.tiles;

import java.util.HashMap;
import java.util.HashMap;

import com.pitchounous.roguelike.entities.Creature;

public class Wall extends Tile {

    public Wall(Integer xPos, Integer yPos) {
        super("wall", "white", xPos, yPos, "black", false);
    }

    public char getGlyph() {
		return 'X';
	}

    @Override
    public void onFootOn(Creature c) {
        // Not  a crossable tile so this does nothing
    }
}
