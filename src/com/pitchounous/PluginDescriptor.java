package com.pitchounous;

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
    private HashMap<String, Object> attributes;
    // As jar file are hardly dynamically imported and loaded to project
    // for security reasons, we are only here checking for file existence
    private ArrayList<String> jarDependencies;
    private ArrayList<String> pluginDependencies;
    private boolean autorun;

    /**
     * 
     * @param pluginName
     * @param description
     * @param className
     * @param version
     * @param language
     * @param attributes
     * @param jarDependencies
     * @param pluginDependencies
     * @param autorun
     */
    public PluginDescriptor(String pluginName, String description, String className, String version, String language,
            HashMap<String, Object> attributes, ArrayList<String> jarDependencies, ArrayList<String> pluginDependencies,
            boolean autorun) {
        this.pluginName = pluginName;
        this.description = description;
        this.className = className;
        this.version = version;
        this.language = language;
        this.attributes = attributes;
        this.jarDependencies = jarDependencies;
        this.pluginDependencies = pluginDependencies;
        this.autorun = autorun;
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

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getJarDependencies() {
        return jarDependencies;
    }

    public void setJarDependencies(ArrayList<String> jarDependencies) {
        this.jarDependencies = jarDependencies;
    }

    public ArrayList<String> getPluginDependencies() {
        return pluginDependencies;
    }

    public void setPluginDependencies(ArrayList<String> pluginDependencies) {
        this.pluginDependencies = pluginDependencies;
    }

    public boolean isAutorun() {
        return autorun;
    }

    public void setAutorun(boolean autorun) {
        this.autorun = autorun;
    }

    /**
     * 
     * @return
     */
    public String getFullClassPath() {
        return "plugins." + getClassName();
    }
}
