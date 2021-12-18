package com.pitchounous.roguelike.world.tiles;

import com.pitchounous.roguelike.entities.creatures.Creature;

public class Wall extends Tile {

    public Wall(int xPos, int yPos) {
        super("wall", "white", xPos, yPos, "black", false);
    }

    public char getGlyph() {
        return 'X';
    }

    @Override
    public void onFootOn(Creature c) {
        // Not a crossable tile so this does nothing
    }
}
