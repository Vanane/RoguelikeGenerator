package com.pitchounous.pluginLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Object identifiant un plugin par son nom, une description, sa version, la langue dans laquelle il est écrit, et une liste de mots-clés permettant de filtrer.
 */
public class PluginDescriptor {
    private String name;
    private String description;
    private String className;    
    private String version;
    private String language;
    private HashMap<String, String> attributes;

    public PluginDescriptor(String name, String description, String className, String version, String language,  HashMap<String, String> attributes) {
        this.name = name;
        this.description = description;
        this.className = className;
        this.version = version;
        this.language = language;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
