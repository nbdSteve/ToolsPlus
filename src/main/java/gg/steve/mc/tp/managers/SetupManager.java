package gg.steve.mc.tp.managers;

import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.attribute.types.OmniToolAttribute;
import gg.steve.mc.tp.cmd.ToolsPlusCmd;
import gg.steve.mc.tp.gui.GuiClickListener;
import gg.steve.mc.tp.gui.GuiManager;
import gg.steve.mc.tp.integration.SellIntegrationManager;
import gg.steve.mc.tp.integration.sell.InternalPriceProvider;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.papi.ToolsPlusExpansion;
import gg.steve.mc.tp.player.listener.HoldToolListener;
import gg.steve.mc.tp.player.listener.PlayerCommandListener;
import gg.steve.mc.tp.player.listener.PlayerToolListener;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.utils.LogUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManager fileManager;
    private static List<PlaceholderExpansion> placeholderExpansions;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManager fm) {
        fileManager = fm;
        Files.CONFIG.load(fm);
        Files.OMNI_CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("t+").setExecutor(new ToolsPlusCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new GuiClickListener(), instance);
        pm.registerEvents(new PlayerToolManager(), instance);
        pm.registerEvents(new HoldToolListener(), instance);
        pm.registerEvents(new PlayerToolListener(), instance);
        pm.registerEvents(new PlayerCommandListener(), instance);
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void registerPlaceholderExpansions(JavaPlugin instance) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansions with it now...");
            new ToolsPlusExpansion(instance).register();
            for (PlaceholderExpansion expansion : placeholderExpansions) {
                expansion.register();
            }
        }
    }

    public static void addPlaceholderExpansion(PlaceholderExpansion expansion) {
        placeholderExpansions.add(expansion);
    }

    public static void loadPluginCache() {
        // modules
        placeholderExpansions = new ArrayList<>();
        ToolConfigDataManager.initialise();
        ModuleManager.loadInstalledModules();
        // gui
        GuiManager.initialise();
        // tools
        ToolsManager.initialiseTools();
        PlayerToolManager.initialise();
        // conditional commands
        PlayerCommandListener.initialiseCommands();
        // price
        InternalPriceProvider.loadPriceMap();
        SellIntegrationManager.initialiseProviderHierarchy();
        // omni
        OmniToolAttribute.loadOmniConfig();
        // cooldown
        CooldownToolAttribute.initialise();
    }

    public static void shutdownPluginCache() {
        //cooldown
        CooldownToolAttribute.shutdown();
        // omni
        OmniToolAttribute.shutdown();
        // price
        SellIntegrationManager.shutdown();
        InternalPriceProvider.shutdown();
        // commands
        PlayerCommandListener.shutdown();
        // tools
        ToolConfigDataManager.shutdown();
        PlayerToolManager.shutdown();
        ToolsManager.shutdown();
        // gui
        GuiManager.shutdown();
        // modules
        ModuleManager.uninstalledAllModules();
        if (placeholderExpansions != null && !placeholderExpansions.isEmpty()) placeholderExpansions.clear();
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
