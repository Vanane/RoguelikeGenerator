package com.pitchounous.roguelike.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.world.tiles.Tile;

public class World {

	public int width;
	public int height;

	Tile[][] tiles;
	Player player;
	List<Creature> creatures;

	public World(Tile[][] tiles, Set<Creature> creatures, Player player) {
		this.creatures = new ArrayList<>(creatures);
		this.creatures.add(player);

		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.player = player;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;
		else
			return tiles[x][y];
	}

	public List<Creature> getAliveCreatures(){
		return creatures.stream().toList();
	}

	public Player getPlayer(){
		return player;
	}

	public Creature getCreatureAt(int x, int y) {
		return creatures.stream()
				.filter(entity -> entity.getX() == x && entity.getY() == y)
				.findFirst()
				.orElse(null);
	}

	/*
	 * Test if a given tile as no creature on it and is a crossable tile
	 */
	public boolean isTileCrossable(int x, int y) {
		return getTile(x, y) != null && getCreatureAt(x, y) == null && tiles[x][y].isCrossable();
	}

	public void update() {
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.get(i);
			// System.out.println(creature.getType()+"  "+creature.hp);
			if (creature.hp <= 0) {
				System.out.println(creature.getType().toUpperCase()+" died");
				creatures.remove(creature);
				if (creature.equals(player)) {
					System.err.println("Game is over, player died ...");
					throw new Error("GAME OVER");
				}
			} else if (!creature.equals(player)) {
				creature.update(this);
			}
		}
	}
}
