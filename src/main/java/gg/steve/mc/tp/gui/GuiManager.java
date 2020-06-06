package gg.steve.mc.tp.gui;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.gui.types.GenericGui;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.utils.YamlFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GuiManager {
    private static Map<String, AbstractGui> guis;

    private GuiManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialise() {
        guis = new HashMap<>();
        int loaded = 0;
        for (String gui : Files.CONFIG.get().getStringList("loaded-guis")) {
            PluginFile file = new YamlFileUtil().load("guis" + File.separator + gui + ".yml", ToolsPlus.get());
            try {
                guis.put(gui, new GenericGui(file.get()));
                loaded++;
            } catch (Exception e) {
                LogUtil.warning("Error while initialising gui: " + gui + ", please check your configuration and verify it is correct.");
            }
        }
        LogUtil.info("Successfully loaded " + loaded + " gui(s) into the plugins cache.");
    }

    public static void shutdown() {
        if (guis != null && !guis.isEmpty()) guis.clear();
    }

    public static AbstractGui getGui(String name) {
        return guis.get(name);
    }
}
