package plugins.grass_tile;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.world.tiles.Tile;

public class Grass extends Tile {

    /**
     * Simple patch of grass. Judging by its length,
     * no lawnmower have been here for a long time ...
     *
     * @param x
     * @param y
     */
    public Grass(int x, int y) {
        super("grass", new Color(83, 135, 12), x, y, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        return '.';
    }

    @Override
    protected void onFootOn(Creature c) {
        // Simple grass, nothing to see here
    }
}
