package plugins.fire_tile;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.world.tiles.Tile;

public class Fire extends Tile {
    /**
     * 
     * @param xpos
     * @param ypos
     */
    public Fire(int xpos, int ypos) {
        super("fire", Color.RED, xpos, ypos, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        return 'X';
    }

    @Override
    protected void onFootOn(Creature c) {
        // Burn, insect !
        System.out.println(c.getType() + " lost 5hp by walking on fire tile !");
        c.setHp(c.getHp() - 5);
    }
}
