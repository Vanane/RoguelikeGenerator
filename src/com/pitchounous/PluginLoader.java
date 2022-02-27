package com.pitchounous;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

// Required as .class files are not generated when javac is called
import plugins.fire_tile.Fire;
import plugins.grass_tile.Grass;
import plugins.terminal_ui.Interface;
import plugins.awt_ui.GameWindow;
import plugins.wolf_creature.Wolf;
import plugins.health_potion.HealthPotion;
import plugins.roguelike.Roguelike;

public class PluginLoader {

	private final static String CONFIG_FILENAME = "plugins.json";
	private static PluginLoader _INSTANCE;

	// Map between base class to inherit and PluginDescriptor for plugins which
	// implement this class
	private HashMap<Class<?>, List<PluginDescriptor>> pluginDescriptors;

	// Map between plugin name and plugin object
	private HashMap<String, Object> loadedPlugins;

	/**
	 * @param pluginInterfaces
	 * @return singleton object created only if not already created previously
	 */
	public static PluginLoader getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new PluginLoader();
		}
		return _INSTANCE;
	}

	/**
	 * Singleton constructor
	 * 
	 * @param pluginInterfaces
	 */
	private PluginLoader() {
		this.pluginDescriptors = new HashMap<>();
		this.loadedPlugins = new HashMap<>();
	}

	/**
	 * Main entry point for the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PluginLoader pl = PluginLoader.getInstance();
		pl.createPluginDescriptors();

		// We check for plugin that need direct instanciation
		for (List<PluginDescriptor> o : pl.pluginDescriptors.values()) {
			for (PluginDescriptor pd : o) {
				if (pd.isAutorun()) {
					List<Object> params = (ArrayList<Object>) pd.getAttributes().get("default_arguments");
					Object[] constructorParams = params.toArray();

					Class<?>[] parameters = new Class<?>[constructorParams.length];
					for (int i = 0; i < parameters.length; i++) {
						Class<?> type = constructorParams[i].getClass();
						System.out.println(constructorParams[i]);

						// Type misscalculation for int
						if (type == Double.class && (Double) constructorParams[i] % 1 == 0) {
							type = int.class;
							constructorParams[i] = ((Double) constructorParams[i]).intValue();
						}
						parameters[i] = type;
					}

					Class<?> pdClass = pl.getPluginDescriptorClass(pd);
					try {
						Object instanciatedPlugin = pdClass.getDeclaredConstructor(parameters)
								.newInstance(constructorParams);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						System.err.println("Unable to run autorun plugin " + pd.getFullClassPath());
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Read plugin file and loads according
	 * PluginDescriptors.
	 */
	private void createPluginDescriptors() {
		List<String> missingJarFile;
		PluginDescriptor[] descriptors = {};

		try {
			descriptors = new GsonBuilder().create().fromJson(
					new FileReader(CONFIG_FILENAME), PluginDescriptor[].class);
		} catch (IOException e) {
			System.err.println("Configuration file not found : " + CONFIG_FILENAME);
		} catch (JsonSyntaxException jse) {
			System.err.println("Configuration file has syntax errors");
			jse.printStackTrace();
		}

		for (PluginDescriptor pd : descriptors) {
			String className = pd.getClassName();
			try {
				// Try to load class to ensure everything is ok
				Class<?> pluginClass = Class.forName(pd.getFullClassPath());
				missingJarFile = new ArrayList<>();
				for (String jarFilename : pd.getJarDependencies()) {
					if (!(new File("lib" + File.separator + jarFilename).exists())) {
						missingJarFile.add(jarFilename);
					}
				}

				if (!missingJarFile.isEmpty()) {
					System.err.println(
							"X - Plugin " + pd.getPluginName()
									+ " could not be loaded as " + missingJarFile + " jar files are missing");
				} else {
					Class<?> motherClass = pluginClass.getSuperclass();
					if (!this.pluginDescriptors.containsKey(motherClass)) {
						this.pluginDescriptors.put(motherClass, new ArrayList<>());
					}
					this.pluginDescriptors.get(motherClass).add(pd);
				}
				System.out.println("Class " + className + " loaded from plugin " + pd.getPluginName());
			} catch (ClassNotFoundException e) {
				System.err.println(
						"X - Class " + pd.getClassName() + " not found for plugin " + pd.getFullClassPath());
			}
		}
		for (Class<?> c : this.pluginDescriptors.keySet()) {
			System.out.println(c + " - " + this.pluginDescriptors.get(c));
		}
	}

	/**
	 * @param intenf
	 * @return
	 *         Return all PluginDecriptor for a given super class
	 */
	public List<PluginDescriptor> getPluginDescriptors(Class<?> intenf) {
		if (this.pluginDescriptors.get(intenf) != null)
			return this.pluginDescriptors.get(intenf);
		return new ArrayList<>();
	}

	/**
	 * @param pd
	 * @return
	 */
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
			if (this.loadedPlugins.containsKey(pd.getPluginName()))
				plugin = this.loadedPlugins.get(pd.getPluginName());
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
