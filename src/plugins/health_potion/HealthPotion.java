package plugins.health_potion;

import java.awt.Color;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.world.tiles.ActivableTile;

public class HealthPotion extends ActivableTile {

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

    @Override
    protected void activateEffect(Creature c) {
        // Player can drink the potion to get some hp
        // once drunk, the empty bottle stays on the
        // cell but has no more effect
        if (c instanceof Player) {
            System.out.println("Player found a health potion and gained 5hp");
            c.setHp(c.getHp() + 5);
        }
    }

}
