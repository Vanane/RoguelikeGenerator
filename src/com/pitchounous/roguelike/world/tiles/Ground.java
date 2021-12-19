package com.pitchounous.roguelike.world.tiles;

import com.pitchounous.roguelike.entities.creatures.Creature;
import java.awt.Color;

public class Ground extends Tile {

    public Ground(int xPos, int yPos) {
        super("ground", new Color(196, 113, 45), xPos, yPos, Color.BLACK, true);
    }

    public char getGlyph() {
        return ' ';
    }

    @Override
    public void onFootOn(Creature c) {
        // Simple dirt ground, nothing to see here
    }
}
