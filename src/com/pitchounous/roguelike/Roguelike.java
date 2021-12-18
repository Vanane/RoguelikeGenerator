package com.pitchounous.roguelike;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Set;

import com.pitchounous.roguelike.entities.creatures.Player;
import com.pitchounous.roguelike.ui.Interface;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.WorldBuilder;

public class Roguelike {

	private boolean isRunning;
	private int framesPerSecond = 60;
	private int timePerLoop = 1000000000 / framesPerSecond;

	private World world;
	private Player player;

	private static final int mapWidth = 100;
	private static final int mapHeight = 100;

	private Interface ui;

	public Roguelike(int screenWidth, int screenHeight, Set<Class<?>> pluginTiles, Set<Class<?>> pluginCreatures) {
		ui = new Interface(screenWidth, screenHeight, new Rectangle(mapWidth, mapHeight));

		createWorld(pluginTiles, pluginCreatures);
	}

	private void createWorld(Set<Class<?>> pluginTiles, Set<Class<?>> pluginCreatures) {
		player = new Player(10, 10);
		world = new WorldBuilder(mapWidth, mapHeight, pluginTiles, pluginCreatures, player)
				.fillWithWall()
				.createRandomWalkCave(12232, 10, 10, 6000)
				.populateWorld(10)
				.build();
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

	public void renderGameOver() {
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
			try {
				update();
			} catch (Error e) {
				renderGameOver();
			}
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
