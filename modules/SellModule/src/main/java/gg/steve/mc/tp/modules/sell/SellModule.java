package gg.steve.mc.tp.modules.sell;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.modules.sell.tool.SellWand;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellModule extends ToolsPlusModule {
    public static String moduleId = "SELL";
    public static String moduleConfigId = "SELL_CONFIG";

    public SellModule() {
        super(moduleId);
        setNiceName("Sell Wand");
    }

    public String getVersion() {
        return "2.0.0-PR1";
    }

    public String getAuthor() {
        return "stevegoodhill";
    }

    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return  new SellWand(nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "sell.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ToolConfigDataManager.addMaterialList(moduleId, FileManager.get(moduleConfigId).getStringList("sellable-containers"));
    }

    @Override
    public void onShutdown() {

    }
}
