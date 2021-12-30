package plugins.grass_tile;

import java.awt.Color;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Grass extends Tile {
    public Grass(int xpos, int ypos) {
        super("grass", new Color(83, 135, 12), xpos, ypos, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        return '.';
    }

    @Override
    public void onFootOn(Creature c) {
        // Simple grass, nothing to see here
    }
}
