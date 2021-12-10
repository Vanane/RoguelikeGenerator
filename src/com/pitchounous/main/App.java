package com.pitchounous.main;

import com.pitchounous.pluginLoader.PluginLoader;

public class App
{
    private String[] arguments;

    public App(String[] args)
    {
        arguments = args;
    }
    
    
    public void run()
    {
        // Load config variables from config.yaml
        // Load plugins from plugins.yaml
        // Start GUI thread
        PluginLoader.getInstance();
    }
}