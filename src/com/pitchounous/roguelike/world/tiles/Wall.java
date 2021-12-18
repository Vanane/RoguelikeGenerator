package com.pitchounous.roguelike.world.tiles;

import java.util.Map;

public class Wall extends Tile {

    public Wall(Map<String, String> tileData, int xpos, int ypos) {
        super(tileData, xpos, ypos, "black", false);
    }

    @Override
    public void onFootOn() {
        // Not  a crossable tile so this does nothing
    }
}
