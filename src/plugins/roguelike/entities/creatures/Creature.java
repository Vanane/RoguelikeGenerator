package plugins.roguelike.entities.creatures;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import plugins.roguelike.entities.Entity;
import plugins.roguelike.entities.behaviours.Behaviour;
import plugins.roguelike.world.World;

public abstract class Creature extends Entity {
	protected World world;
	protected Behaviour behaviour;
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
	 * @param dx
	 * @param dy
	 */
	public void move(int dx, int dy) {
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
	 */
	public void update() {
		behaviour.update();
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

	public void attachToWorld(World world) throws Exception {
		if (this.world != null)
			throw new Exception("Creature already bound to a world");
		this.world = world;
	}

	public void setBehaviour(Class<?> b) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (b == null)
			throw new InvocationTargetException(null,
					"Given Behaviour for " + this.getClass().getSimpleName() + " was null");
		this.behaviour = (Behaviour) b.getDeclaredConstructor(new Class<?>[] { Creature.class }).newInstance(this);
	}
}
