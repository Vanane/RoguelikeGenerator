package plugins.roguelike.world.tiles;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;

public class Ground extends Tile {

    /**
     * 
     * @param xPos
     * @param yPos
     */
    public Ground(int xPos, int yPos) {
        super("ground", new Color(196, 113, 45), xPos, yPos, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        return '.';
    }

    @Override
    public void onFootOn(Creature c) {
        // Simple dirt ground, nothing to see here
    }
}
