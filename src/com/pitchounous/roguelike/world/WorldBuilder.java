package com.pitchounous.roguelike.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.pitchounous.roguelike.entities.Creature;
import com.pitchounous.roguelike.world.tiles.Ground;
import com.pitchounous.roguelike.world.tiles.Tile;
import com.pitchounous.roguelike.world.tiles.Wall;

public class WorldBuilder {
	int width;
	int height;

	Tile[][] tiles;
	Set<Creature> creatures;

	Map<String, Map<String, String>> tileData;
	Map<String, Map<String, String>> creatureData;

	public WorldBuilder(
			Map<String, Map<String, String>> tileData,
			Map<String, Map<String, String>> creatureData,
			int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		this.tileData = tileData;
		this.creatureData = creatureData;
		this.creatures = new HashSet<Creature>();
	}

	public WorldBuilder load(String file) {
		// Loads map from file
		return this;
	}

	public Tile createTile(String type, int x, int y) {
		Tile tile = null;
		if (type.equals("ground")){
			tile = new Ground(tileData.get(type), x, y);
		}else if(type.equals("wall")){
			tile = new Wall(tileData.get(type), x, y);
		}
		return tile;
	}

	public Creature createCreature(String type, int x, int y) {
		return new Creature(creatureData.get(type), x, y);
	}

	public World build() {
		return new World(tiles, creatures);
	}

	public WorldBuilder fillWithWall() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Wall(tileData.get("wall"), x, y);
			}
		}
		return this;
	}

	public WorldBuilder addBorders() {
		for (int x = 0; x < width; x++) {
			tiles[x][0] = createTile("wall", x, 0);
			tiles[x][height - 1] = createTile("wall", x, height - 1);
		}

		for (int y = 0; y < height; y++) {
			tiles[0][y] = createTile("wall", 0, y);
			tiles[width - 1][y] = createTile("wall", width - 1, y);
		}
		return this;
	}

	public WorldBuilder carveOutRoom(int topX, int topY, int width, int height) {
		for (int x = topX; x < topX + width; x++) {
			for (int y = topY; y < topY + height; y++) {
				tiles[x][y] = createTile("ground", x, y);
			}
		}
		return this;
	}

	public WorldBuilder populateWorld(int nrOfCreatures) {
		Random rnd = new Random();
		int rndX;
		int rndY;

		List<String> creatureTypes = new ArrayList<String>(creatureData.keySet());
		creatureTypes.remove("player");

		for (int i = 0; i < nrOfCreatures; i++) {

			// Find an empty cell to add a creature
			do {
				rndX = rnd.nextInt(width);
				rndY = rnd.nextInt(height);
			} while (!(tiles[rndX][rndY] instanceof Ground));

			String creatureType = creatureTypes.get(rnd.nextInt(creatureTypes.size()));
			creatures.add(createCreature(creatureType, rndX, rndY));
		}

		return this;
	}

	public WorldBuilder createRandomWalkCave(int seed, int startX, int startY, int length) {
		Random rnd = new Random(seed);
		int direction;
		int x = startX;
		int y = startY;

		for (int i = 0; i < length; i++) {
			direction = rnd.nextInt(4);
			if (direction == 0 && (x + 1) < (width - 1)) {
				x += 1;
			} else if (direction == 1 && (x - 1) > 0) {
				x -= 1;
			} else if (direction == 2 && (y + 1) < (height - 1)) {
				y += 1;
			} else if (direction == 3 && (y - 1) > 0) {
				y -= 1;
			}

			tiles[x][y] = createTile("ground", x, y);
		}

		return this;
	}

}