package com.pitchounous.roguelike.entities;

import com.pitchounous.roguelike.world.World;

import java.util.Map;
import java.util.Random;

public class Creature extends Entity {

	public Creature(Map<String, String> creatureData, int x, int y) {
		super(creatureData, x, y);
	}

	public void move(World world, int dx, int dy) {
		if (world.isBlocked(x + dx, y + dy) != true) {
			x += dx;
			y += dy;
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
