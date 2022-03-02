package plugins.health_potion;

import java.awt.Color;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.entities.creatures.Player;
import plugins.roguelike.world.tiles.ActivableTile;

public class HealthPotion extends ActivableTile {

    /**
     * Simple Health potion lying on the dirty floor
     * The ancestor of the blue potion of Fortnite
     * 
     * @param xPos
     * @param yPos
     */
    public HealthPotion(int xPos, int yPos) {
        super("heal potion", Color.PINK, xPos, yPos, Color.BLACK, true);
    }

    @Override
    public char getGlyph() {
        if (hasBeenActivated) {
            return '.';
        }
        return 'H';
    }

    /**
     * Player can drink the potion to get some hp
     * once drunk, the empty bottle stays on the
     * cell but has no more effect
     */
    @Override
    protected void activateEffect(Creature c) {
        if (c instanceof Player) {
            System.out.println("Player found a health potion and gained 5hp");
            c.setHp(c.getHp() + 5);
        }
    }

}
