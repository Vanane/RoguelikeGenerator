package com.pitchounous.roguelike;

import java.util.Set;

import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.ui.Window;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.WorldBuilder;

public class Roguelike {
	private World world;
	private Player player;

	private static final int mapWidth = 100;
	private static final int mapHeight = 100;

	public Roguelike(int screenWidth, int screenHeight, Set<Class<?>> pluginTiles, Set<Class<?>> pluginCreatures) {
		createWorld(pluginTiles, pluginCreatures);
		new Window(world);
	}

	private void createWorld(Set<Class<?>> pluginTiles, Set<Class<?>> pluginCreatures) {
		player = new Player(10, 10);
		world = new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, player)
				.fillWithWall()
				.createRandomWalkCave(12232, 10, 10, 6000)
				.populateWorld(10)
				.build();
	}
}
