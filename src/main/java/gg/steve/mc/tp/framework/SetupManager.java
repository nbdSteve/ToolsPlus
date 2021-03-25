package gg.steve.mc.tp.framework;

import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.attribute.types.OmniToolAttribute;
import gg.steve.mc.tp.cmd.ToolsPlusCmd;
import gg.steve.mc.tp.framework.gui.GuiClickListener;
import gg.steve.mc.tp.framework.gui.GuiManager;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibManager;
import gg.steve.mc.tp.integration.providers.FactionsProvider;
import gg.steve.mc.tp.integration.sell.PriceProviderType;
import gg.steve.mc.tp.integration.sell.SellIntegrationManager;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.papi.ToolsPlusExpansion;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.listener.HoldToolListener;
import gg.steve.mc.tp.player.listener.PlayerCommandListener;
import gg.steve.mc.tp.player.listener.PlayerToolListener;
import gg.steve.mc.tp.tool.ToolsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManagerUtil fileManagerUtil;
    private static List<PlaceholderExpansion> placeholderExpansions;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManagerUtil fm) {
        fileManagerUtil = fm;
        Files.CONFIG.load(fm);
        Files.OMNI_CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("t+").setExecutor(new ToolsPlusCmd());
        instance.getCommand("t+").setTabCompleter(new ToolsPlusCmd());
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
        // libs
        ToolsPlusLibManager.loadInstalledLibs();
        FactionsProvider.loadProvider();
        // gui
        GuiManager.initialise();
        // tools
        ToolsManager.initialiseTools();
        PlayerToolManager.initialise();
        // conditional commands
        PlayerCommandListener.initialiseCommands();
        // price
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
        PriceProviderType.INTERNAL.getPriceProvider().shutdown();
        // commands
        PlayerCommandListener.shutdown();
        // tools
        ToolConfigDataManager.shutdown();
        PlayerToolManager.shutdown();
        ToolsManager.shutdown();
        // gui
        GuiManager.shutdown();
        // libs
        ToolsPlusLibManager.uninstalledAllLibs();
        // modules
        ModuleManager.uninstalledAllModules();
        if (placeholderExpansions != null && !placeholderExpansions.isEmpty()) placeholderExpansions.clear();
    }

    public static void setupMetrics(JavaPlugin instance, int id) {
        Metrics metrics = new Metrics(instance, id);
        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
    }

    public static FileManagerUtil getFileManagerUtil() {
        return fileManagerUtil;
    }
}
