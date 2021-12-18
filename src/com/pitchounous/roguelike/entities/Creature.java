package com.pitchounous.roguelike.entities;

import java.util.Random;

import com.pitchounous.roguelike.world.World;

public class Creature extends Entity {

	public Creature(String type, String colorString, Integer xPos, Integer yPos) {
		super(type, colorString, xPos, yPos);
	}

	public void move(World world, int dx, int dy) {
		if (world.isTileCrossable(x + dx, y + dy)) {
			x += dx;
			y += dy;
			world.getTile(x, y).onStep(this);
		}
	}

	public void update(World world) {
		Random rnd = new Random();
		int performAction = rnd.nextInt(100);
		if (performAction > 98) {

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
