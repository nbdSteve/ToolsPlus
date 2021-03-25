package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.tool.utils.ToolLoaderUtil;
import gg.steve.mc.tp.framework.yml.utils.YamlFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToolsManager {
    private static Map<String, AbstractTool> tools;
    private static Map<UUID, PlayerTool> serverTools;

    private ToolsManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialiseTools() {
        tools = new HashMap<>();
        serverTools = new HashMap<>();
        for (String tool : Files.CONFIG.get().getStringList("loaded-tools")) {
            PluginFile file = new YamlFileUtil().load("tools" + File.separator + tool + ".yml", ToolsPlus.get());
            if (!ModuleManager.isInstalled(file.get().getString("type").toUpperCase())) {
                ((YamlFileUtil) file).delete();
                LogUtil.info("Error while loading tool: " + tool + ", the required module (" + file.get().getString("type").toUpperCase() + ") is not installed.");
                continue;
            }
            ToolLoaderUtil loader = new ToolLoaderUtil(file, tool);
            tools.put(tool, loader.getTool());
        }
        LogUtil.info("Successfully loaded " + tools.size() + " tool(s) into the plugins cache.");
    }

    public static void shutdown() {
        if (tools != null && !tools.isEmpty()) tools.clear();
        if (serverTools != null && !serverTools.isEmpty()) serverTools.clear();
    }

    public static AbstractTool getTool(String name) {
        return tools.get(name);
    }

    public static boolean isPlayerToolRegistered(UUID toolId) {
        if (serverTools == null || serverTools.isEmpty()) return false;
        return serverTools.containsKey(toolId);
    }

    public static boolean addPlayerTool(UUID toolId, NBTItem item) {
        if (isPlayerToolRegistered(toolId)) return false;
        return serverTools.put(toolId, new PlayerTool(toolId, item)) != null;
    }

    public static boolean removePlayerTool(UUID toolId) {
        if (!isPlayerToolRegistered(toolId)) return false;
        return serverTools.remove(toolId) != null;
    }

    public static PlayerTool getPlayerTool(UUID toolId) {
        if (!isPlayerToolRegistered(toolId)) return null;
        return serverTools.get(toolId);
    }

    public static String getAbstractToolCount() {
        return String.valueOf(tools.size());
    }

    public static String getPlayerToolCount() {
        return String.valueOf(serverTools.size());
    }

    public static String getAbstractToolsAsList() {
        StringBuilder message = new StringBuilder();
        if (tools.size() > 0) {
            int i = 0;
            for (String name : tools.keySet()) {
                message.append(name);
                if (i != tools.size() - 1) {
                    message.append(", ");
                }
                i++;
            }
        }
        return message.toString();
    }

    public static String getToolAmount(ToolsPlusModule module) {
        int amount = 0;
        for (AbstractTool tool : tools.values()) {
            if (tool.getModuleId().equalsIgnoreCase(module.getIdentifier())) amount++;
        }
        return ToolsPlus.formatNumber(amount);
    }

    public static String getAbstractName(AbstractTool tool) {
        for (Map.Entry entry : tools.entrySet()) {
            if (entry.getValue().equals(tool)) return (String) entry.getKey();
        }
        return "Error";
    }

    public static Map<String, AbstractTool> getTools() {
        return tools;
    }
}