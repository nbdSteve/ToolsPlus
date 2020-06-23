package gg.steve.mc.tp.gui.types;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.gui.utils.GuiItemUtil;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.upgrade.UpgradeType;
import gg.steve.mc.tp.utils.CommandUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GenericGui extends AbstractGui {
    private ConfigurationSection section;

    public GenericGui(ConfigurationSection section) {
        super(section, section.getString("type"), section.getInt("size"));
        this.section = section;
        List<Integer> slots = section.getIntegerList("fillers.slots");
        ItemStack filler = GuiItemUtil.createItem(section.getConfigurationSection("fillers"));
        for (Integer slot : slots) {
            setItemInSlot(slot, filler, player -> {});
        }
    }

    @Override
    public void refresh(PlayerTool tool) {
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                continue;
            }
            ItemStack item;
            List<Integer> slots = section.getIntegerList(entry + ".slots");
            switch (section.getString(entry + ".action").split(":")[0]) {
                case "condition":
                    switch (section.getString(entry + ".action").split(":")[1]) {
                        case "upgrade":
                            UpgradeType upgrade = UpgradeType.valueOf(section.getString(entry + ".action").split(":")[2].toUpperCase());
                            item = GuiItemUtil.createConditionalItem(section.getConfigurationSection(entry), tool, upgrade);
                            String condition = "true";
                            if (!GuiItemUtil.isConditionMet(section.getConfigurationSection(entry), tool, upgrade))
                                condition = "false";
                            for (Integer slot : slots) {
                                String finalCondition = condition;
                                setItemInSlot(slot, item, player -> {
                                    if (tool.getUpgradeLevel(upgrade) + 1 != GuiItemUtil.getConditionLevel(section.getConfigurationSection(entry))) {
                                        return;
                                    }
                                    if (!tool.getAbstractTool().getUpgrade(upgrade).doUpgrade(player, tool)) {
                                        player.closeInventory();
                                        return;
                                    }
                                    CommandUtil.execute(section.getStringList(entry + "." + finalCondition + ".commands"), player);
                                    refresh(tool);
                                });
                            }
                            break;
                        case "permission":
                            break;
                    }
                    break;
                case "mode-switch":
                    ModeType mode = ModeType.valueOf(section.getString(entry + ".action").split(":")[1].toUpperCase());
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            if (!tool.getModeChange(mode).isChangingEnabled()) return;
                            if (!tool.getModeChange(mode).changeMode(player, tool)) {
                                player.closeInventory();
                            } else {
                                CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                                refresh(tool);
                            }
                        });
                    }
                    break;
                case "downgrade":
                    UpgradeType upgrade = UpgradeType.valueOf(section.getString(entry + ".action").split(":")[1].toUpperCase());
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            if (!tool.getAbstractTool().getUpgrade(upgrade).isDegradable()) return;
                            if (!tool.getAbstractTool().getUpgradeManager().getUpgrade(upgrade).doDowngrade(player, tool)) {
                                player.closeInventory();
                            } else {
                                CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                                refresh(tool);
                            }
                        });
                    }
                    break;
                case "uses":
                    AbstractCurrency currency = CurrencyType.getCurrencyFromString(section.getString(entry + ".action").split(":")[1].toLowerCase());
                    int amount = Integer.parseInt(section.getString(entry + ".action").split(":")[2]);
                    double cost = Double.parseDouble(section.getString(entry + ".action").split(":")[3]);
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            if (!tool.getAbstractTool().getAttributeManager().isAttributeEnabled(ToolAttributeType.USES)) {
                                return;
                            }
                            if (!tool.getAbstractTool().getAttributeManager().getAttribute(ToolAttributeType.USES).doIncrease(player, tool, currency, amount, cost)) {
                                player.closeInventory();
                            } else {
                                CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                                refresh(tool);
                            }
                        });
                    }
                    break;
                case "back":
                    break;
                case "gui":
                    break;
                case "purchase":
                    break;
                case "close":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            player.closeInventory();
                        });
                    }
                    break;
                case "none":
                default:
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> CommandUtil.execute(section.getStringList(entry + ".commands"), player));
                    }
                    break;
            }
        }
    }
}
