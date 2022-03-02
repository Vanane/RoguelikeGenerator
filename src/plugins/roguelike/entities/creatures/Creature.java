package plugins.roguelike.entities.creatures;

import java.awt.Color;
import java.util.Random;

import plugins.roguelike.entities.Entity;
import plugins.roguelike.world.World;

public abstract class Creature extends Entity {

	int hp;
	int attack;

	/**
	 * Base abstract class to create new creature like zombie or sheep
	 * 
	 * @param type
	 * @param color
	 * @param hp
	 * @param attack
	 * @param xPos
	 * @param yPos
	 */
	public Creature(String type, Color color, int hp, int attack, int xPos, int yPos) {
		super(type, color, Color.BLACK, xPos, yPos);
		this.hp = hp;
		this.attack = attack;
	}

	/**
	 * Move the creature to another cell of the board
	 * 
	 * @param world
	 * @param dx
	 * @param dy
	 */
	public void move(World world, int dx, int dy) {
		if (world.isTileCrossable(x + dx, y + dy)) {
			x += dx;
			y += dy;
			world.getTile(x, y).onStep(this);
		} else if (Creature.class.isInstance(world.getCreatureAt(x + dx, y + dy))) {
			Creature c = world.getCreatureAt(x + dx, y + dy);
			c.hp -= this.attack;
			System.out.println(
					c.getType() + " get attacked by " + this.getType() + " and lost " + this.attack + " hp");
		}
	}

	/**
	 * Update the game state by a 'tick', in this case we move the creatures and
	 * make them attack if necessary
	 * 
	 * @param world
	 */
	public void update(World world) {
		Random rnd = new Random();
		int performAction = rnd.nextInt(100);
		if (performAction > 98) {

			// Moving on a cell with another creature on it make
			// this creature attacking the other
			int rndNr = rnd.nextInt(3);
			if (rndNr == 0) {
				move(world, 1, 0);
			} else if (rndNr == 1) {
				move(world, -1, 0);
			} else if (rndNr == 2) {
				move(world, 0, 1);
			} else if (rndNr == 3) {
				move(world, 0, -1);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 
	 * @param hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
}
