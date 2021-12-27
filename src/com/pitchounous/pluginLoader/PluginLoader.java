package com.pitchounous.pluginLoader;

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

// Useful import beside ↓ (if nothing's here you won't be able to add plugins)
import plugins.Grass;
import plugins.Wolf;
import plugins.Fire;

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

			PluginDescriptor[] descriptors = new GsonBuilder().create().fromJson(
					new FileReader(CONFIG_FILENAME),
					PluginDescriptor[].class);
			for (PluginDescriptor pd : descriptors) {
				String className = pd.getClassName();
				try {
					// Try to load class to ensure everything is ok
					Class<?> pluginClass = Class.forName(className);
					for (Class<?> acceptedClass : acceptedClassPlugins) {
						if (acceptedClass.isAssignableFrom(pluginClass)) {
							if (!pluginDescriptors.containsKey(acceptedClass))
								pluginDescriptors.put(acceptedClass, new ArrayList<>());
							pluginDescriptors.get(acceptedClass).add(pd);
						}
					}

					System.out.println("Plugin " + pd.getName() + " loaded for class " + className);
				} catch (ClassNotFoundException e) {
					System.out.println(
							"Class " + pd.getClassName() + " not found for plugin " + pd.getName() + ", skipping.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return all PluginDecriptor for a given super class
	 */
	public List<PluginDescriptor> getPluginDescriptors(Class<?> intenf) {
		if(pluginDescriptors.get(intenf) != null) return pluginDescriptors.get(intenf);
		return new ArrayList<>();
	}

	public Class<?> getPluginDescriptorClass(PluginDescriptor pd) {
		try {
			return Class.forName(pd.getClassName());
		} catch (ClassNotFoundException e) {
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
			if (loadedPlugins.containsKey(pd.getName()))
				plugin = loadedPlugins.get(pd.getName());
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
			System.out.println("Le plugin " + pd.getName() + "n'a pas pu être chargé :");
			e.printStackTrace();
		}
		return plugin;
	}

	/**
	 * Instanciate directly class with passed arguments
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

			System.out.println("Constructor available for :   " + pluginClass.getDeclaredConstructors()[0]);
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
