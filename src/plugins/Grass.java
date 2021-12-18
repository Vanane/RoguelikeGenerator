package plugins;

import com.pitchounous.roguelike.entities.Creature;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Grass extends Tile {
    public Grass(Integer xpos, Integer ypos) {
        super("grass", "green", xpos, ypos, "black", true);
    }

    public char getGlyph() {
		return '.';
	}

    @Override
    public void onFootOn(Creature c) {
        // Simple grass, nothing to see here
    }
}
