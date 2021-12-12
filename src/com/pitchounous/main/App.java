package com.pitchounous.main;

import com.pitchounous.pluginLoader.PluginLoader;
import com.pitchounous.roguelike.Roguelike;

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
        Roguelike game = new Roguelike(80, 24);
		game.run();
    }
}