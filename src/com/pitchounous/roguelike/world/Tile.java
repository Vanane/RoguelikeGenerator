package com.pitchounous.roguelike.world;

import com.pitchounous.roguelike.Roguelike;
import com.pitchounous.roguelike.entities.Entity;

import java.awt.Color;
import java.util.Map;

public class Tile extends Entity {

    private Color backgroundColor;
    private boolean blocked = false;

    public boolean isBlocked() {
        return this.blocked;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public Tile(Map<String, String> tileData, int xpos, int ypos) {
        super(tileData, xpos, ypos);
        backgroundColor = Roguelike.stringToColor(tileData.get("backgroundColor"));
        blocked = Boolean.valueOf(tileData.get("blocked"));
    }
}