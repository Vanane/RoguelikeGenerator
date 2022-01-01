package plugins.fire_tile;

import java.awt.Color;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.tiles.Tile;

public class Fire extends Tile {
    public Fire(int xpos, int ypos) {
        super("fire", Color.RED, xpos, ypos, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        return 'X';
    }

    @Override
    public void onFootOn(Creature c) {
        // Burn, insect !
        System.out.println(c.getType()+" lost 5hp by walking on fire tile !");
        c.hp -= 5;
    }
}
