package com.pitchounous.roguelike.entities.creatures;

import java.awt.Color;
import java.util.Random;

import com.pitchounous.roguelike.entities.Entity;
import com.pitchounous.roguelike.world.World;

public class Creature extends Entity {

	public int attack;
	public int hp;

	public Creature(String type, Color color, int hp, int attack, int xPos, int yPos) {
		super(type, color, Color.BLACK, xPos, yPos);
		this.hp = hp;
		this.attack = attack;
	}

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

	public void update(World world) {
		Random rnd = new Random();
		int performAction = rnd.nextInt(100);
		if (performAction > 50) {

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
}