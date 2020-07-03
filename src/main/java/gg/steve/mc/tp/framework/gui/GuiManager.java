package gg.steve.mc.tp.framework.gui;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.gui.types.GenericGui;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.utils.YamlFileUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class GuiManager {
    private static Map<String, YamlConfiguration> guis;
    private static Map<UUID, Map<String, AbstractGui>> playerGuis;

    private GuiManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialise() {
        guis = new HashMap<>();
        playerGuis = new HashMap<>();
        int loaded = 0;
        for (String gui : Files.CONFIG.get().getStringList("loaded-guis")) {
            PluginFile file = new YamlFileUtil().load("guis" + File.separator + gui + ".yml", ToolsPlus.get());
            try {
                guis.put(gui, file.get());
                loaded++;
            } catch (Exception e) {
                LogUtil.warning("Error while initialising gui: " + gui + ", please check your configuration and verify it is correct.");
            }
        }
        LogUtil.info("Successfully loaded " + loaded + " gui(s) into the plugins cache.");
    }

    public static void shutdown() {
        if (guis != null && !guis.isEmpty()) guis.clear();
        if (playerGuis != null && !playerGuis.isEmpty()) playerGuis.clear();
    }

    public static AbstractGui getGui(String name, Player player) {
        if (!playerGuis.containsKey(player.getUniqueId())) {
            playerGuis.put(player.getUniqueId(), new HashMap<>());
        }
        if (!playerGuis.get(player.getUniqueId()).containsKey(name)) {
            playerGuis.get(player.getUniqueId()).put(name, new GenericGui(guis.get(name), player, name));
        }
        return playerGuis.get(player.getUniqueId()).get(name);
    }

    public static void open(String name, Player player, PlayerTool tool) {
        getGui(name, player).refresh(tool);
        getGui(name, player).open(player);
    }
}
