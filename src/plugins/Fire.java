package plugins;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Fire extends Tile {
    public Fire(int xpos, int ypos) {
        super("fire", "red", xpos, ypos, "black", true);
    }

    public char getGlyph() {
        return '8';
    }

    @Override
    public void onFootOn(Creature c) {
        // Burn, insect !
        c.hp -= 5;
    }
}
