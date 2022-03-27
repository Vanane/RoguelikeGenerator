package plugins.roguelike.world.tiles;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;

public class Wall extends Tile {

    /**
     * A really simple wall in brick
     *
     * @param xPos
     * @param yPos
     */
    public Wall(int xPos, int yPos) {
        super("wall", Color.BLACK, xPos, yPos, Color.BLACK, false);
    }

    @Override
    public char getGlyph() {
        return 'X';
    }

    @Override
    protected void onFootOn(Creature c) {
        // Not a crossable tile so this does nothing
    }
}
