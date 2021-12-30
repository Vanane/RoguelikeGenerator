package com.pitchounous.pluginLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Object identifying a plugin by its name, a description, its version, the
 * language it is written in, and a list of keywords to filter
 */
public class PluginDescriptor {
    private String pluginName;
    private String description;
    private String className;
    private String version;
    private String language;
    private HashMap<String, String> attributes;
    private ArrayList<String> jarDependencies;

    public PluginDescriptor(String pluginName, String description, String className, String version, String language,
            HashMap<String, String> attributes, ArrayList<String> jarDependencies) {
        this.pluginName = pluginName;
        this.description = description;
        this.className = className;
        this.version = version;
        this.language = language;
        this.attributes = attributes;
        this.jarDependencies = jarDependencies;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getJarDependencies() {
        return jarDependencies;
    }

    public void setJarDependencies(ArrayList<String> jarDependencies) {
        this.jarDependencies = jarDependencies;
    }

    public String getFullClassPath() {
        return "plugins." + getPluginName() + "." + getClassName();
    }
}
