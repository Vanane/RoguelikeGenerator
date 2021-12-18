package com.pitchounous.pluginLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.GsonBuilder;

import plugins.Grass;
import plugins.Fire;
import plugins.Wolf;

public class PluginLoader {

	private final static String CONFIG_FILENAME = "plugins.json";
	private static PluginLoader _INSTANCE;

	// Map between class name and PluginDescriptor
	private HashMap<String, PluginDescriptor> pluginDescriptors;
	// Map between plugin name and plugin object
	private HashMap<String, Object> loadedPlugins;

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
			for (PluginDescriptor p : descriptors) {
				try {
					String className = p.getClassName();
					// Tenter de récupérer la classe. exception si elle n'existe pas
					Class<?> pluginClass = Class.forName(className);

					if (!pluginDescriptors.containsKey(className))
						pluginDescriptors.put(className, p);
					pluginDescriptors.get(className);
					System.out.println("Plugin " + p.getName() + " loaded for class " + className);
				} catch (ClassNotFoundException e) {
					System.out.println(
							"Class " + p.getClassName() + " not found for plugin " + p.getName() + ", skipping.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Singleton de la classe.
	 */
	public static PluginLoader getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new PluginLoader();
		}
		return _INSTANCE;
	}

	/**
	 * Retourne les descriptions de tous les plugins implémentant ou héritant de
	 * <b>intenf</b>.
	 */
	public List<PluginDescriptor> getPluginDescriptors(Class<?> intenf) {
		List<PluginDescriptor> inheritedPlugins = new ArrayList<>();
		for (PluginDescriptor pd : pluginDescriptors.values()) {
			Class<?> pluginClass = null;
			try {
				pluginClass = Class.forName(pd.getClassName());
			} catch (ClassNotFoundException e) {
			}

			if (intenf.isAssignableFrom(pluginClass)) {
				inheritedPlugins.add(pd);
			}
		}
		return inheritedPlugins;
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
	 * Retourne une instance du PluginDescriptor passé en paramètre.
	 * 
	 * @param pd
	 * @param args
	 * @return
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
}
