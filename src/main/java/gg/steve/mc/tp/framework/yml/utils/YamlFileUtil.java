package gg.steve.mc.tp.framework.yml.utils;

import gg.steve.mc.tp.framework.yml.PluginFile;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Class that handles creating or loading a plugin file from the resources folder
 */
public class YamlFileUtil extends PluginFile {
    private Plugin instance;
    //YAML configuration for the file
    private YamlConfiguration yamlFile;
    //File to be created
    private File file;
    //Store the name of the file
    private String fileName;

    /**
     * Creates the provided yml file, the filename must be that of a file in the resources folder
     *
     * @param fileName String, the name of the file to load
     */
    @Override
    public PluginFile load(String fileName, JavaPlugin instance) {
        this.instance = instance;
        this.fileName = fileName;
        this.file = new File(instance.getDataFolder(), fileName);
        if (!file.exists()) {
            instance.saveResource(fileName, false);
            instance.getLogger().info("The internal YAML file: " + fileName + " was not found, actively creating / loading it now.");
        }
        yamlFile = new YamlConfiguration();
        try {
            yamlFile.load(file);
        } catch (InvalidConfigurationException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " is not in the correct format, please check your YAML syntax.");
        } catch (FileNotFoundException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " was not found, please contact the developer. Disabling the plugin.");
            instance.getServer().getPluginManager().disablePlugin(instance);
        } catch (IOException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " could not be loaded, please contact the developer. Disabling the plugin.");
            instance.getServer().getPluginManager().disablePlugin(instance);
        }
        return this;
    }

    /**
     * Saves the file, this is used for setting variables in a method
     */
    @Override
    public void save() {
        try {
            yamlFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads the file, updates the values
     */
    @Override
    public void reload() {
        try {
            yamlFile.load(file);
        } catch (InvalidConfigurationException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " is not in the correct format, please check your YAML syntax.");
        } catch (FileNotFoundException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " was not found, please contact the developer. Disabling the plugin.");
            instance.getServer().getPluginManager().disablePlugin(instance);
        } catch (IOException e) {
            instance.getLogger().severe("The supplied file " + fileName +
                    " could not be loaded, please contact the developer. Disabling the plugin.");
            instance.getServer().getPluginManager().disablePlugin(instance);
        }
    }

    /**
     * Gets the file configuration for this file
     *
     * @return FileConfiguration
     */
    @Override
    public YamlConfiguration get() {
        return yamlFile;
    }
}