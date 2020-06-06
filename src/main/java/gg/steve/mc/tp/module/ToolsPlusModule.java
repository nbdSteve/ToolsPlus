package gg.steve.mc.tp.module;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

public abstract class ToolsPlusModule {
    private String identifier;

//    public ToolsPlusModule(ModuleType moduleType) {
//        this.moduleType = moduleType;
//    }

    public ToolsPlusModule(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        StringBuilder builder = new StringBuilder();
        builder.append(identifier, 0, 1);
        builder.append(identifier.substring(1).toLowerCase());
        builder.append("Module");
        return builder.toString();
    }

    public String getNiceName() {
        StringBuilder builder = new StringBuilder();
        builder.append(identifier, 0, 1);
        builder.append(identifier.substring(1).toLowerCase());
        return builder.toString();
    }

    //    public ModuleType getModuleType() {
//        return moduleType;
//    }
    public String getIdentifier() {
        return identifier;
    }

    public ToolsPlus getToolsPlus() {
        return ToolsPlus.get();
    }

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract List<Listener> getListeners();

    public abstract PlaceholderExpansion getPlaceholderExpansion();

    public abstract AbstractTool loadTool(NBTItem item, PluginFile file);

    public abstract Map<String, String> getModuleFiles();

    public abstract void onLoad();
}