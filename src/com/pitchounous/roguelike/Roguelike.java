package com.pitchounous.roguelike;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

import com.pitchounous.roguelike.entities.Creature;
import com.pitchounous.roguelike.ui.Interface;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.WorldBuilder;

public class Roguelike {

	private boolean isRunning;
	private int framesPerSecond = 60;
	private int timePerLoop = 1000000000 / framesPerSecond;

	private World world;
	private Creature player;

	private HashMap<String, HashMap<String, String>> creatureData;

	private static final int mapWidth = 100;
	private static final int mapHeight = 100;

	private Interface ui;

	public Roguelike(int screenWidth, int screenHeight, Set<Class<?>> pluginTiles) {
		ui = new Interface(screenWidth, screenHeight, new Rectangle(mapWidth, mapHeight));

		creatureData = loadData(Paths.get("src", "com", "pitchounous", "roguelike", "creatures.csv").toString());

		createWorld(pluginTiles);
	}

	public HashMap<String, HashMap<String, String>> loadData(String file) {
		HashMap<String, HashMap<String, String>> entityMap = new HashMap<>();
		String line = "";
		String[] attributeNames = new String[10];

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			line = br.readLine();

			if (line != null) {
				attributeNames = line.split(", ");
			}

			while ((line = br.readLine()) != null) {
				String[] data = line.split(", ");
				HashMap<String, String> entityData = new HashMap<>();

				for (int i = 0; i < attributeNames.length; i++) {
					entityData.put(attributeNames[i], data[i]);
				}

				String name = data[1];
				entityMap.put(name, entityData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return entityMap;
	}

	private void createWorld(Set<Class<?>> pluginTiles) {
		player = new Creature("player", "white", 10, 10);
		world = new WorldBuilder(creatureData, mapWidth, mapHeight, pluginTiles)
				.fillWithWall()
				.createRandomWalkCave(12232, 10, 10, 6000)
				.populateWorld(10)
				.build();
		world.player = player;
		world.addEntity(player);
	}

	public void processInput() {
		InputEvent event = ui.getNextInput();
		if (event instanceof KeyEvent) {
			KeyEvent keypress = (KeyEvent) event;
			switch (keypress.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.move(world, -1, 0);
					break;
				case KeyEvent.VK_RIGHT:
					player.move(world, 1, 0);
					break;
				case KeyEvent.VK_UP:
					player.move(world, 0, -1);
					break;
				case KeyEvent.VK_DOWN:
					player.move(world, 0, 1);
					break;
			}
		}
	}

	public void render() {
		ui.pointCameraAt(world, player.getX(), player.getY());
		ui.refresh();
	}

	public void update() {
		world.update();
	}

	public void run() {
		isRunning = true;

		while (isRunning) {
			long startTime = System.nanoTime();

			processInput();
			update();
			render();

			long endTime = System.nanoTime();

			long sleepTime = timePerLoop - (endTime - startTime);

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Color stringToColor(String colorString) {
		Color color;
		try {
			Field field = Color.class.getField(colorString);
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null;
		}
		return color;
	}

}
