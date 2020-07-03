package gg.steve.mc.tp.tool.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.utils.ItemBuilderUtil;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.tool.AbstractTool;
import org.bukkit.configuration.ConfigurationSection;

public class ToolLoaderUtil {
    private PluginFile file;
    private String name;
    private AbstractTool tool;
    private NBTItem item;

    public ToolLoaderUtil(PluginFile file, String name) {
        this.file = file;
        this.name = name;
        String moduleId = file.get().getString("type").toUpperCase();
        loadItem(moduleId);
        tool = ModuleManager.getInstalledModule(moduleId).loadTool(this.item, file);
        tool.setUsesGuiName(file.get().getString("uses.gui"));
        loadToolData();
    }

    public void loadItem(String moduleId) {
        ConfigurationSection section = this.file.get().getConfigurationSection("item");
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name")
                .replace("{tool-mode}", file.get().getStringList("modes.tool.track").get(0).split(":")[2])
                .replace("{sell-mode}", file.get().getStringList("modes.sell.track").get(0).split(":")[2]));
        builder.setLorePlaceholders("{radius-upgrade}", "{modifier-upgrade}", "{uses}", "{mined}", "{tool-mode}", "{sell-mode}", "{cane-mined}");
        builder.addLore(section.getStringList("lore"),
                file.get().getStringList("upgrades.radius.track").get(0).split(":")[3],
                file.get().getStringList("upgrades.modifier.track").get(0).split(":")[3],
                ToolsPlus.formatNumber(file.get().getInt("uses.starting")),
                "0", file.get().getStringList("modes.tool.track").get(0).split(":")[2],
                file.get().getStringList("modes.sell.track").get(0).split(":")[2], "0");
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(moduleId, this.name, this.file);
        this.item = builder.getNbtItem();
    }

    public void loadToolData() {
        tool.loadToolData(this.file);
    }

    public AbstractTool getTool() {
        return tool;
    }
}
