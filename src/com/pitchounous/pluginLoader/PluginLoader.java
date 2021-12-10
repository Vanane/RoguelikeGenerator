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
    
    private Map<Class<?>, List<PluginDescriptor>> pluginDescriptors;
    private Map<Class<?>, List<Object>> loadedPlugins;

    /**
     * Constructeur privé de la classe Singleton.
     * Lit le fichier des plugins et instancie les PluginDescriptors
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
                    Class<?> pClass = Class.forName(p.getClassName());
                    if(!pluginDescriptors.containsKey(pClass)) pluginDescriptors.put(pClass, null);
                    pluginDescriptors.get(pClass).add(p);
                    System.out.println("Plugin " + p.getName() + " loaded for class " + pClass.getName());
                }
                catch(ClassNotFoundException e) {
                    System.out.println("Class " + p.getClassName() + " not found for plugin " + p.getName() + ", skipping.");
                }     
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return
     */
    public static PluginLoader getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new PluginLoader();
        }
        return _INSTANCE;
    }

    /**
     * Retourne les descriptions de tous les plugins implémentant ou héritant de <b>intenf</b>.
     */
    public List<PluginDescriptor> getPluginDescriptors(Class<?> intenf) {
        return pluginDescriptors.get(intenf);
    }

    /**
     * 
     * @param pd
     * @return
     */
    public Object loadPlugin(PluginDescriptor pd) {
        return null;
    }

    /**
     * Retourne un objet PluginDescriptor à partir de son nom.
     * @return
     */
    private PluginDescriptor getPluginDescriptor(String pluginName)
    {
        return null;
        /*
        if(pluginName == null) return null;
        Properties plugin = this.FILE_PROPERTIES.
        PluginDescriptor desc = new PluginDescriptor(
            
            description,
            className,
            version,
            language,
            attributes
        );*/
    }
}
