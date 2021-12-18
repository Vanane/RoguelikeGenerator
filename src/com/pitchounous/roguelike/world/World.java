package com.pitchounous.roguelike.world;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import com.pitchounous.roguelike.entities.Creature;
import com.pitchounous.roguelike.entities.Entity;
import com.pitchounous.roguelike.world.tiles.Tile;

public class World {

	private int width;
	private int height;

	private Tile[][] tiles;
	public Creature player;
	public Set<Creature> creatures;

	public World(Tile[][] tiles) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}

	public World(Tile[][] tiles, Set<Creature> creatures) {
		this.creatures = new HashSet<>();
		this.creatures.addAll(creatures);
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}

	public void addEntity(Creature creature) {
		this.creatures.add(creature);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;
		else
			return tiles[x][y];
	}

	public char glyph(int x, int y) {
		return getTile(x, y).getGlyph();
	}

	public Color glyphColor(int x, int y) {
		return getTile(x, y).getColor();
	}

	public Color backgroundColor(int x, int y) {
		return getTile(x, y).getBackgroundColor();
	}

	public Entity getEntityAt(int x, int y) {
		return creatures.stream()
				.filter(entity -> entity.getX() == x && entity.getY() == y)
				.findFirst()
				.orElse(null);
	}

	/*
	* Test if a given tile as no creature on it and is a crossable tile
	*/
	public boolean isTileCrossable(int x, int y) {
		if (getTile(x, y) == null)
			return false;
		return (getEntityAt(x, y) == null && tiles[x][y].isCrossable());
	}

	public void update() {
		creatures.stream()
				.filter(creature -> !creature.getType().equals("player"))
				.forEach(creature -> creature.update(this));
	}
}
