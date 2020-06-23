package gg.steve.mc.tp.modules.tray;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tray.tool.TrayTool;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayModule extends ToolsPlusModule {
    public static String moduleId = "TRAY";
    public static String moduleConfigId = "TRAY_CONFIG";

    public TrayModule() {
        super(moduleId);
        setNiceName("Tray Tool");
    }

    @Override
    public String getVersion() {
        return "2.0.0-PR1";
    }

    @Override
    public String getAuthor() {
        return "stevegoodhill";
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new TrayTool(nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "tray.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ToolConfigDataManager.addMaterialList(moduleId, FileManager.get(moduleConfigId).getStringList("whitelist"));
    }

    @Override
    public void onShutdown() {

    }
}
