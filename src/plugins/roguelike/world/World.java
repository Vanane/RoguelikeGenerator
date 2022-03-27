package plugins.roguelike.world;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import plugins.roguelike.entities.creatures.Creature;
import plugins.roguelike.entities.creatures.Player;
import plugins.roguelike.world.tiles.Tile;

public class World {

	public int width;
	public int height;

	Tile[][] tiles;
	Player player;
	List<Creature> creatures;

	/**
	 * World that stores game state, creatures and tiles
	 *
	 * @param tiles
	 * @param creatures
	 * @param player
	 */
	public World(Tile[][] tiles, Set<Creature> creatures, Player player) {
		this.creatures = new ArrayList<>(creatures);
		try {
			player.attachToWorld(this);
		} catch (Exception e) {
			System.out.println("Player already attached to a world, can't continue");
			System.exit(1);
		}
		for (Creature creature : creatures) {
			try {
				creature.attachToWorld(this);
			} catch (Exception e) {
				System.out.println("Creature already attached to a world, skipping");
			}
		}

		this.creatures.add(player);

		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.player = player;
	}

	/**
	 * Return the tile at a specific location
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;
		return tiles[x][y];
	}

	/**
	 * Get Alive Creatures
	 *
	 * @return
	 */
	public List<Creature> getAliveCreatures() {
		return creatures.stream().toList();
	}

	/**
	 * Return the player
	 *
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Retrieve a creature from a given position if there is one
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Creature getCreatureAt(int x, int y) {
		return creatures.stream()
				.filter(entity -> entity.getX() == x && entity.getY() == y)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Test if a given tile as no creature on it and is crossable
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTileCrossable(int x, int y) {
		return getTile(x, y) != null && getCreatureAt(x, y) == null && tiles[x][y].isCrossable();
	}

	/**
	 * Make alive creatures moves or attack adjacent ones
	 */
	public void update() {
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.get(i);
			// System.out.println(creature.getType()+" "+creature.hp);
			if (creature.getHp() <= 0) {
				System.out.println(creature.getType().toUpperCase() + " died");
				creatures.remove(creature);
				if (creature.equals(player)) {
					System.err.println("Game is over, player died ...");
					throw new Error("GAME OVER");
				}

				// Making other creatures moving
			} else if (!creature.equals(player)) {
				creature.update();
			}
		}
	}

	/**
	 * Process keyboard arrow inputs
	 *
	 * @param ke
	 */
	public void processInput(KeyEvent ke) {
		// Move the player according to the last input
		if (ke == null)
			return;

		System.out.println("Touch pressed: " + ke.getKeyCode());
		switch (ke.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.move(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				player.move(1, 0);
				break;
			case KeyEvent.VK_UP:
				player.move(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				player.move(0, 1);
				break;
		}
	}
}
