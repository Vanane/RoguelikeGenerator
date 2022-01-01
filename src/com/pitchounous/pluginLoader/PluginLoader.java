package com.pitchounous.pluginLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.ui.BasicUI;
import com.pitchounous.roguelike.world.tiles.Tile;

import plugins.fire_tile.Fire;
import plugins.grass_tile.Grass;
import plugins.terminal_ui.Interface;
import plugins.wolf_creature.Wolf;

public class PluginLoader {

	private final static String CONFIG_FILENAME = "plugins.json";
	private static PluginLoader _INSTANCE;
	private Class<?>[] acceptedClassPlugins = {
			Tile.class, Creature.class, BasicUI.class
	};

	// Map between base class to inherit and PluginDescriptor for plugins which
	// implement this class
	private HashMap<Class<?>, List<PluginDescriptor>> pluginDescriptors;

	// Map between plugin name and plugin object
	private HashMap<String, Object> loadedPlugins;

	public static PluginLoader getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new PluginLoader();
		}
		return _INSTANCE;
	}

	/**
	 * Singleton constructor
	 * Read plugin file and loads according PluginDescriptors.
	 */
	private PluginLoader() {
		try {
			pluginDescriptors = new HashMap<>();
			loadedPlugins = new HashMap<>();
			boolean missingJarFile;

			PluginDescriptor[] descriptors = new GsonBuilder().create().fromJson(
					new FileReader(CONFIG_FILENAME),
					PluginDescriptor[].class);

			for (PluginDescriptor pd : descriptors) {
				String className = pd.getClassName();
				try {
					// Try to load class to ensure everything is ok
					Class<?> pluginClass = Class.forName(pd.getFullClassPath());
					for (Class<?> acceptedClass : acceptedClassPlugins) {
						if (acceptedClass.isAssignableFrom(pluginClass)) {
							missingJarFile = false;
							for (String jarFilename : pd.getJarDependencies()) {
								if (!(new File("lib" + File.separator + jarFilename).exists())) {
									missingJarFile = true;
								}
							}

							if (!missingJarFile) {
								if (!pluginDescriptors.containsKey(acceptedClass))
									pluginDescriptors.put(acceptedClass, new ArrayList<>());
								pluginDescriptors.get(acceptedClass).add(pd);
							} else {
								System.err.println(
										"X - Plugin " + pd.getPluginName()
												+ " could not be loaded as some jar files are missing");

							}
						}
					}
					System.out.println("Class " + className + " loaded from plugin " + pd.getPluginName());
				} catch (ClassNotFoundException e) {
					System.err.println(
							"X - Class " + pd.getClassName() + " not found for plugin " + pd.getFullClassPath());
				}
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return all PluginDecriptor for a given super class
	 */
	public List<PluginDescriptor> getPluginDescriptors(Class<?> intenf) {
		if (pluginDescriptors.get(intenf) != null)
			return pluginDescriptors.get(intenf);
		return new ArrayList<>();
	}

	public Class<?> getPluginDescriptorClass(PluginDescriptor pd) {
		try {
			return Class.forName(pd.getFullClassPath());
		} catch (ClassNotFoundException e) {
			// Should never happened as it has already been tested
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Instanciate a plugin using according PluginDescriptor and passed arguments
	 * 
	 * @param pd
	 * @param args
	 */
	public Object instanciatePluginClass(PluginDescriptor pd, Object[] args) {
		Object plugin = null;
		try {
			if (loadedPlugins.containsKey(pd.getPluginName()))
				plugin = loadedPlugins.get(pd.getPluginName());
			else {
				Class<?> pluginClass = Class.forName(pd.getClassName());
				Class<?>[] constructorParameters = new Class[args.length];

				for (int i = 0; i < args.length; i++) {
					Class<?> type = args[i].getClass();
					constructorParameters[i] = type;
				}

				System.out.println("Constructor available for :   " + pluginClass.getDeclaredConstructors()[0]);
				Constructor<?> c = pluginClass.getDeclaredConstructor(constructorParameters);
				plugin = c.newInstance(args);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Le plugin " + pd.getPluginName() + "n'a pas pu être chargé :");
			e.printStackTrace();
		}
		return plugin;
	}

	/**
	 * Instanciate directly class with passed arguments
	 * 
	 * @param pd
	 * @param args
	 */
	public Object instanciatePluginClass(Class<?> pluginClass, Object[] args) {
		Object plugin = null;
		try {
			Class<?>[] constructorParameters = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				Class<?> type = args[i].getClass();
				constructorParameters[i] = type;
			}

			// System.out.println("Constructor available for : " +
			// pluginClass.getDeclaredConstructors()[0]);
			Constructor<?> c = pluginClass.getDeclaredConstructor(constructorParameters);
			plugin = c.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("La classe " + pluginClass.getSimpleName() + "n'a pas pu être chargé :");
			e.printStackTrace();
		}
		return plugin;
	}
}
