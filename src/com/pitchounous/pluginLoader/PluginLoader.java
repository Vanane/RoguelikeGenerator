package com.pitchounous.pluginLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.google.gson.*;

public class PluginLoader {

	private final static String CONFIG_FILENAME = "plugins.json";
	private final static String PACKAGE_NAME = "com.pitchounous";
	private static Properties FILE_PROPERTIES = new Properties();
	private static PluginLoader _INSTANCE;

	/**
	 * Map contenant, pour chaque nom de classe, les PluginDescriptors qui
	 * l'implémentent
	 */
	private Map<String, List<PluginDescriptor>> pluginDescriptors;
	/** Map contenant, pour chaque nom de plugin, l'instance du plugin */
	private Map<String, Object> loadedPlugins;

	/**
	 * Constructeur privé de la classe Singleton.
	 * Lit le fichier des plugins et instancie les PluginDescriptors.
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
						pluginDescriptors.put(className, null);
					pluginDescriptors.get(className).add(p);
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
		return pluginDescriptors.get(intenf);
	}

	/**
	 * Retourne une instance du PluginDescriptor passé en paramètre.
	 * 
	 * @param pd
	 * @return
	 */
	public Object loadPlugin(PluginDescriptor pd) {
		Object plugin = null;
		try {
			if (loadedPlugins.containsKey(pd.getName()))
				plugin = loadedPlugins.get(pd.getName());
			else {
				Class<?> pluginClass = Class.forName(pd.getClassName());
				plugin = pluginClass.getConstructor().newInstance();
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Le plugin " + pd.getName() + "n'a pas pu être chargé :");
			e.printStackTrace();
		}
		return plugin;
	}

	/**
	 * Retourne un objet PluginDescriptor à partir de son nom.
	 * 
	 * @return
	 */
	private PluginDescriptor getPluginDescriptor(String pluginName) {
		return null;
		/*
		 * if(pluginName == null) return null;
		 * Properties plugin = this.FILE_PROPERTIES.
		 * PluginDescriptor desc = new PluginDescriptor(
		 * 
		 * description,
		 * className,
		 * version,
		 * language,
		 * attributes
		 * );
		 */
	}
}
