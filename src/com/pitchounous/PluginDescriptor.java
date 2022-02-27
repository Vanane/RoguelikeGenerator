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
    // As jar file are hardly dynamicaly imported and loaded to project
    // for security reasons, we are only here checking for file existance
    private ArrayList<String> jarDependencies;
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
     * @param autorun
     */
    public PluginDescriptor(String pluginName, String description, String className, String version, String language,
            HashMap<String, Object> attributes, ArrayList<String> jarDependencies, boolean autorun) {
        this.pluginName = pluginName;
        this.description = description;
        this.className = className;
        this.version = version;
        this.language = language;
        this.attributes = attributes;
        this.jarDependencies = jarDependencies;
        this.autorun = autorun;
    }

    /**
     * 
     * @return
     */
    public String getPluginName() {
        return pluginName;
    }

    /**
     * 
     * @param pluginName
     */
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    /**
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     */
    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     */
    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getJarDependencies() {
        return jarDependencies;
    }

    /**
     * 
     * @param jarDependencies
     */
    public void setJarDependencies(ArrayList<String> jarDependencies) {
        this.jarDependencies = jarDependencies;
    }

    /**
     * 
     * @return
     */
    public boolean isAutorun() {
        return autorun;
    }

    /**
     * 
     * @param autorun
     */
    public void setAutorun(boolean autorun) {
        this.autorun = autorun;
    }

    /**
     * 
     * @return
     */
    public String getFullClassPath() {
        return "plugins." + getPluginName() + "." + getClassName();
    }
}
