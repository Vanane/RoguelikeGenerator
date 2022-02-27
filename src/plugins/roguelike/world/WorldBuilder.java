package plugins.roguelike.world;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.entities.creatures.Player;
import plugins.roguelike.entities.creatures.Sheep;
import plugins.roguelike.entities.creatures.Zombie;
import plugins.roguelike.world.tiles.Ground;
import plugins.roguelike.world.tiles.Tile;
import plugins.roguelike.world.tiles.Wall;

public class WorldBuilder {
	int width;
	int height;

	Tile[][] tiles;
	List<Class<?>> availableTileTypes;
	List<Class<?>> availableCreatureTypes;

	Set<Creature> creatures;

	Player player;

	/**
	 * 
	 * @param width
	 * @param height
	 * @param pluginTiles
	 * @param pluginCreatures
	 * @param player
	 */
	public WorldBuilder(int width, int height,
			Set<Class<?>> pluginTiles, Set<Class<?>> pluginCreatures,
			Player player) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		creatures = new HashSet<Creature>();

		// Set ensure only one occurence of the class
		pluginCreatures.add(Zombie.class);
		pluginCreatures.add(Sheep.class);
		availableCreatureTypes = new ArrayList<Class<?>>(pluginCreatures);

		// Set ensure only one occurence of the class
		pluginTiles.add(Ground.class);
		availableTileTypes = new ArrayList<Class<?>>(pluginTiles);

		this.player = player;
	}

	/**
	 * 
	 * @param type
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile createTile(String type, int x, int y) {
		Tile tile = null;
		Random rnd = new Random();

		if (type.equals("ground")) {
			// Could add some probability here
			Class<?> tileType = availableTileTypes.get(rnd.nextInt(availableTileTypes.size()));
			Class<?>[] parameters = { int.class, int.class };
			try {
				tile = (Tile) tileType.getDeclaredConstructor(parameters).newInstance(x, y);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			}
		} else if (type.equals("wall")) {
			tile = new Wall(x, y);
		}
		return tile;
	}

	/**
	 * 
	 * @param creatureType
	 * @param x
	 * @param y
	 * @return
	 */
	public Creature createCreature(Class<?> creatureType, int x, int y) {
		Creature c = null;

		Class<?>[] parameters = { int.class, int.class };
		try {
			c = (Creature) creatureType.getDeclaredConstructor(parameters).newInstance(x, y);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
		}
		return c;
	}

	/**
	 * 
	 * @return
	 */
	public World build() {
		return new World(tiles, creatures, player);
	}

	/**
	 * 
	 * @return
	 */
	public WorldBuilder fillWithWall() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = new Wall(x, y);
			}
		}
		return this;
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @param topX
	 * @param topY
	 * @param width
	 * @param height
	 * @return
	 */
	public WorldBuilder carveOutRoom(int topX, int topY, int width, int height) {
		for (int x = topX; x < topX + width; x++) {
			for (int y = topY; y < topY + height; y++) {
				tiles[x][y] = createTile("ground", x, y);
			}
		}
		return this;
	}

	/**
	 * 
	 * @param nrOfCreatures
	 * @return
	 */
	public WorldBuilder populateWorld(int nrOfCreatures) {
		Random rnd = new Random();
		int rndX;
		int rndY;

		for (int i = 0; i < nrOfCreatures; i++) {

			// Find an empty cell to add a creature
			do {
				rndX = rnd.nextInt(width);
				rndY = rnd.nextInt(height);
			} while (!(tiles[rndX][rndY] instanceof Ground));

			Class<?> creatureType = availableCreatureTypes.get(rnd.nextInt(availableCreatureTypes.size()));
			creatures.add(createCreature(creatureType, rndX, rndY));
		}

		return this;
	}

	/**
	 * 
	 * @param seed
	 * @param startX
	 * @param startY
	 * @param length
	 * @return
	 */
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