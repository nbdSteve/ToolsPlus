package gg.steve.mc.tp.modules.tray.tool;

import gg.steve.mc.tp.attribute.types.BlocksMinedToolAttribute;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.attribute.types.UsesToolAttribute;
import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.mode.types.SellModeChange;
import gg.steve.mc.tp.mode.types.ToolTypeModeChange;
import gg.steve.mc.tp.modules.tray.TrayModule;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.upgrade.types.ModifierUpgrade;
import gg.steve.mc.tp.upgrade.types.RadiusUpgrade;
import org.bukkit.configuration.file.YamlConfiguration;

public class TrayTool extends AbstractTool {

    public TrayTool(NBTItem item, PluginFile file) {
        super(TrayModule.moduleId, item, file);
        YamlConfiguration config = file.get();
        if (config.getBoolean("uses.enabled")) {
            getAttributeManager().addToolAttribute(new UsesToolAttribute(config.getString("uses.lore-update-string")));
        }
        if (config.getBoolean("blocks-mined.enabled")) {
            getAttributeManager().addToolAttribute(new BlocksMinedToolAttribute(config.getString("blocks-mined.lore-update-string")));
        }
        if (config.getBoolean("cooldown.enabled")) {
            getAttributeManager().addToolAttribute(new CooldownToolAttribute(config.getInt("cooldown.duration")));
        }
        getUpgradeManager().addToolUpgrade(new RadiusUpgrade(file));
        getUpgradeManager().addToolUpgrade(new ModifierUpgrade(file));
        getModeChangeManager().addToolMode(new ToolTypeModeChange(file));
        getModeChangeManager().addToolMode(new SellModeChange(file));
    }

    @Override
    public YamlConfiguration getModuleConfig() {
        return FileManager.get("TRAY_CONFIG");
    }

    @Override
    public void loadToolData(PluginFile pluginFile) {
        setData(new TrayData());
    }
}
