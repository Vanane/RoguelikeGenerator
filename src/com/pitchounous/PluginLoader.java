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

public class PluginLoader {

	private final static String CONFIG_FILENAME = "plugins.json";
	private static PluginLoader _INSTANCE;

	// Map between base class to inherit and PluginDescriptor for plugins which
	// implement this class
	private HashMap<Class<?>, List<PluginDescriptor>> pluginDescriptors;

	/**
	 * @param pluginInterfaces
	 * @return Singleton object created only if not already created previously
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
	}

	/**
	 * Main entry point for the program
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		PluginLoader pl = PluginLoader.getInstance();
		pl.createPluginDescriptors();

		// We check for plugin that need direct instantiation
		for (List<PluginDescriptor> o : pl.pluginDescriptors.values()) {
			for (PluginDescriptor pd : o) {
				if (pd.isAutorun()) {
					List<Object> params = (ArrayList<Object>) pd.getAttributes().get("default_arguments");
					Object[] constructorParams = params.toArray();
					Class<?> pdClass = pl.getPluginDescriptorClass(pd);

					pl.instantiatePluginClass(pdClass, constructorParams);
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
				Class<?> pluginClass = Class.forName(pd.getClassName());
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
						"X - Class " + pd.getClassName() + " not found for plugin " + pd.getClassName());
			}
		}
	}

	/**
	 * @param baseClass
	 * @return all PluginDescriptor for a given super class
	 */
	public List<PluginDescriptor> getPluginDescriptors(Class<?> baseClass) {
		List<PluginDescriptor> descriptors = this.pluginDescriptors.get(baseClass);
		return (descriptors == null) ? new ArrayList<>() : descriptors;
	}

	/**
	 * @param pd
	 * @return
	 */
	public Class<?> getPluginDescriptorClass(PluginDescriptor pd) {
		try {
			System.out.println(pd.getClassName());
			System.out.println(pd.getPluginName());
			return Class.forName(pd.getClassName());
		} catch (ClassNotFoundException e) {
			// Should never happened as it has already been tested
			e.printStackTrace();
		}
		return null;
	}

	public PluginDescriptor createPluginDescriptorFromClass(Class<?> c, HashMap<String, Object> attributes) {
		return new PluginDescriptor(
				c.getSimpleName(),
				"Default plugin creation from inside the project for " + c.getClass(),
				c.getName(),
				"1.0",
				"en",
				attributes,
				new ArrayList<>(),
				new ArrayList<>(),
				false);
	}

	/**
	 * Instantiate directly class with passed arguments
	 *
	 * @param pd
	 * @param args
	 */
	public Object instantiatePluginClass(Class<?> pluginClass, Object[] args) {
		Object plugin = null;
		try {
			Class<?>[] constructorTypes = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				Class<?> type = args[i].getClass();

				// Type miss calculation for int
				if (type == Double.class && (Double) args[i] % 1 == 0) {
					type = int.class;
					args[i] = ((Double) args[i]).intValue();
				}
				constructorTypes[i] = type;
			}
			
			plugin = instantiatePluginClass(pluginClass, args, constructorTypes);
		} catch (IllegalArgumentException | SecurityException e) {
			System.err.println("La classe " + pluginClass.getSimpleName() + " n'a pas pu ??tre charg??e");
			e.printStackTrace();
		}
		return plugin;
	}
	
	/**
	 * Instantiate directly a class with passed arguments, and their respective types.
	 * @param pluginClass
	 * @param args
	 * @param types
	 * @return
	 */
	public Object instantiatePluginClass(Class<?> pluginClass, Object[] args, Class[] types) {
		Object plugin = null;
		try {
			Constructor<?> c = pluginClass.getDeclaredConstructor(types);
			plugin = c.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.err.println("La classe " + pluginClass.getSimpleName() + " n'a pas pu ??tre charg??e");
			e.printStackTrace();
		}
		return plugin;
	}

}
