package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.attribute.ToolAttributeManager;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.mode.AbstractModeChange;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.mode.ToolModeChangeManager;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.ToolUpgradeManager;
import gg.steve.mc.tp.upgrade.UpgradeType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class AbstractTool {
//    private ToolType type;
    private String moduleId;
    private ToolData data;
    private NBTItem item;
    private PluginFile config;
    private ToolAttributeManager attributeManager;
    private ToolUpgradeManager upgradeManager;
    private ToolModeChangeManager modeChangeManager;
    private AbstractGui usesGui;

//    public AbstractTool(ToolType type, NBTItem item, PluginFile config) {
//        this.type = type;
//        this.item = item;
//        this.config = config;
//        this.attributeManager = new ToolAttributeManager();
//        this.upgradeManager = new ToolUpgradeManager();
//        this.modeChangeManager = new ToolModeChangeManager();
//    }

    public AbstractTool(String moduleId, NBTItem item, PluginFile config) {
        this.moduleId = moduleId;
        this.item = item;
        this.config = config;
        this.attributeManager = new ToolAttributeManager();
        this.upgradeManager = new ToolUpgradeManager();
        this.modeChangeManager = new ToolModeChangeManager();
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public ToolsPlusModule getModule() {
        return ModuleManager.getInstalledModule(getModuleId());
    }

    public void setData(ToolData data) {
        this.data = data;
    }

    public YamlConfiguration getConfig() {
        return this.config.get();
    }

    public ToolData getData() {
        return data;
    }

    public AbstractUpgrade getUpgrade(UpgradeType type) {
        if (upgradeManager.isUpgradeEnabled(type)) return upgradeManager.getUpgrade(type);
        return AbstractUpgrade.defaultUpgrade;
    }

    public ItemStack getItemStack() {
        item.setString("tools+.uuid", String.valueOf(UUID.randomUUID()));
        return item.getItem();
    }

    public NBTItem getItem() {
        return item;
    }

    public void setUsesGui(AbstractGui gui) {
        this.usesGui = gui;
    }

    public AbstractGui getUsesGui() {
        return usesGui;
    }

    public ToolAttributeManager getAttributeManager() {
        return attributeManager;
    }

    public ToolUpgradeManager getUpgradeManager() {
        return upgradeManager;
    }

    public AbstractModeChange getModeChange(ModeType type) {
        if (modeChangeManager.isModeChangeEnabled(type)) return modeChangeManager.getModeChange(type);
        return modeChangeManager.getModeChange(ModeType.NONE);
    }

    public ToolModeChangeManager getModeChangeManager() {
        return modeChangeManager;
    }

    public abstract YamlConfiguration getModuleConfig();

    public abstract void loadToolData(PluginFile file);
}