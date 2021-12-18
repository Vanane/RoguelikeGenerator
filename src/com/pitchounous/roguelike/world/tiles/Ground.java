package com.pitchounous.roguelike.world.tiles;

import java.util.Map;

public class Ground extends Tile {

    public Ground(Map<String, String> tileData, int xpos, int ypos) {
        super(tileData, xpos, ypos, "black", true);
    }

    @Override
    public void onFootOn() {
        // Simple dirt ground, nothing to see here
    }
}
