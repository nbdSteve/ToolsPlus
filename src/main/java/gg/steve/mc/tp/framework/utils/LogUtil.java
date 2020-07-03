package gg.steve.mc.tp.framework.utils;

import gg.steve.mc.tp.ToolsPlus;

public class LogUtil {

    public static void info(String message) {
        ToolsPlus.get().getLogger().info(message);
    }

    public static void warning(String message) {
        ToolsPlus.get().getLogger().warning(message);
    }
}
