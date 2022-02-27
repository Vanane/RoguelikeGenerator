# Add new plugins

`plugins.json` file lists all the plugins that the application will load on startup.
Template to add a new plugin to the file:
```json
{
    "pluginName":"myPlugin",
    "description":"This is my plugin",
    "className":"MyPlugin",
    "version":"1.0",
    "language":"FR",
    "jarDependencies": [],
    "autorun": false,
    "attributes":
    {
        "difficulty":"ez"
    }
}
```