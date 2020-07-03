package gg.steve.mc.tp.modules.trench;

import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.trench.tool.TrenchTool;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrenchModule extends ToolsPlusModule {
    public static String moduleId = "TRENCH";
    public static String moduleConfigId = "TRENCH_CONFIG";

    public TrenchModule() {
        super(moduleId);
        setNiceName("Trench Tool");
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

    @Override
    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new TrenchTool(nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "trench.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ToolConfigDataManager.addMaterialList(moduleId, FileManagerUtil.get(moduleConfigId).getStringList("blacklist"));
    }

    @Override
    public void onShutdown() {

    }
}
