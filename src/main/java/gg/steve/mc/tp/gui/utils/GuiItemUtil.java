package gg.steve.mc.tp.gui.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.upgrade.UpgradeType;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class GuiItemUtil {

    public static ItemStack createItem(ConfigurationSection section) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addLore(section.getStringList("lore"));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(section.getBoolean("unbreakable"));
        return builder.getItem();
    }

    public static ItemStack createItem(ConfigurationSection section, PlayerTool tool) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{radius-current-upgrade}",
                "{radius-next-upgrade}",
                "{radius-upgrade-cost}",
                "{radius-upgrade-cost-relative}",
                "{radius-upgrade-level}",
                "{radius-upgrade-max}",
                "{radius-upgrade-currency-prefix}",
                "{radius-upgrade-currency-suffix}",
                "{modifier-current-upgrade}",
                "{modifier-next-upgrade}",
                "{modifier-upgrade-cost}",
                "{modifier-upgrade-cost-relative}",
                "{modifier-upgrade-level}",
                "{modifier-upgrade-max}",
                "{modifier-upgrade-currency-prefix}",
                "{modifier-upgrade-currency-suffix}",
                "{tool-current-mode}",
                "{tool-next-mode}",
                "{tool-mode-change-cost}",
                "{tool-mode-change-cost-relative}",
                "{tool-mode-currency-prefix}",
                "{tool-mode-currency-suffix}",
                "{sell-current-mode}",
                "{sell-next-mode}",
                "{sell-mode-change-cost}",
                "{sell-mode-change-cost-relative}",
                "{sell-mode-currency-prefix}",
                "{sell-mode-currency-suffix}",
                "{uses}");
        builder.addLore(section.getStringList("lore"),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS)),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS))),
                getRelativeString(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS)),
                        tool, "radius", tool.getUpgradeLevel(UpgradeType.RADIUS) + 1, tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency()),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getSuffix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER)),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER))),
                getRelativeString(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER)),
                        tool, "modifier", tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1, tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency()),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getSuffix(),
                tool.getModeChange(ModeType.TOOL).getCurrentModeLore(tool.getCurrentMode(ModeType.TOOL)),
                tool.getModeChange(ModeType.TOOL).getNextModeLore(tool.getCurrentMode(ModeType.TOOL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL))),
                getRelativeString(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL)),
                        tool, "tool", tool.getModeChange(ModeType.TOOL).getNextMode(tool.getCurrentMode(ModeType.TOOL)), tool.getModeChange(ModeType.TOOL).getCurrency()),
                tool.getModeChange(ModeType.TOOL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.TOOL).getCurrency().getSuffix(),
                tool.getModeChange(ModeType.SELL).getCurrentModeLore(tool.getCurrentMode(ModeType.SELL)),
                tool.getModeChange(ModeType.SELL).getNextModeLore(tool.getCurrentMode(ModeType.SELL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.SELL).getChangePriceForMode(tool.getCurrentMode(ModeType.SELL))),
                getRelativeString(tool.getModeChange(ModeType.SELL).getChangePriceForMode(tool.getCurrentMode(ModeType.SELL)),
                        tool, "sell", tool.getModeChange(ModeType.SELL).getNextMode(tool.getCurrentMode(ModeType.SELL)), tool.getModeChange(ModeType.SELL).getCurrency()),
                tool.getModeChange(ModeType.SELL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.SELL).getCurrency().getSuffix(),
                ToolsPlus.formatNumber(tool.getUses()));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(section.getBoolean("unbreakable"));
        return builder.getItem();
    }

    public static ItemStack createConditionalItem(ConfigurationSection section, PlayerTool tool, UpgradeType upgrade) {
        String condition = "false";
        if (isConditionMet(section, tool, upgrade)) condition = "true";
        int level = getConditionLevel(section);
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString(condition + ".material"), section.getString(condition + ".data"));
        builder.addName(section.getString(condition + ".name"));
        builder.setLorePlaceholders("{radius-current-upgrade}",
                "{radius-next-upgrade}",
                "{radius-upgrade-cost}",
                "{radius-upgrade-cost-relative}",
                "{radius-upgrade-level}",
                "{radius-upgrade-max}",
                "{radius-upgrade-currency-prefix}",
                "{radius-upgrade-currency-suffix}",
                "{modifier-current-upgrade}",
                "{modifier-next-upgrade}",
                "{modifier-upgrade-cost}",
                "{modifier-upgrade-cost-relative}",
                "{modifier-upgrade-level}",
                "{modifier-upgrade-max}",
                "{modifier-upgrade-currency-prefix}",
                "{modifier-upgrade-currency-suffix}",
                "{condition-radius-upgrade-cost}",
                "{condition-radius-upgrade-cost-compounded}",
                "{condition-radius-upgrade-level}",
                "{condition-modifier-upgrade-cost}",
                "{condition-modifier-upgrade-cost-compounded}",
                "{condition-modifier-upgrade-level}",
                "{tool-current-mode}",
                "{tool-next-mode}",
                "{tool-mode-change-cost}",
                "{tool-mode-change-cost-relative}",
                "{tool-mode-currency-prefix}",
                "{tool-mode-currency-suffix}",
                "{sell-current-mode}",
                "{sell-next-mode}",
                "{sell-mode-change-cost}",
                "{sell-mode-change-cost-relative}",
                "{sell-mode-currency-prefix}",
                "{sell-mode-currency-suffix}",
                "{uses}");
        builder.addLore(section.getStringList(condition + ".lore"),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS)),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS))),
                getRelativeString(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS)),
                        tool, "radius", level, tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency()),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getSuffix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER)),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER))),
                getRelativeString(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER)),
                        tool, "modifier", level, tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency()),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getSuffix(),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(level)),
                ToolsPlus.formatNumber(getCompoundPrice(tool, UpgradeType.RADIUS, section)),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(level),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(level)),
                ToolsPlus.formatNumber(getCompoundPrice(tool, UpgradeType.MODIFIER, section)),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(level),
                tool.getModeChange(ModeType.TOOL).getCurrentModeLore(tool.getCurrentMode(ModeType.TOOL)),
                tool.getModeChange(ModeType.TOOL).getNextModeLore(tool.getCurrentMode(ModeType.TOOL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL))),
                getRelativeString(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL)),
                        tool, "tool", level, tool.getModeChange(ModeType.TOOL).getCurrency()),
                tool.getModeChange(ModeType.TOOL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.TOOL).getCurrency().getSuffix(),
                tool.getModeChange(ModeType.SELL).getCurrentModeLore(tool.getCurrentMode(ModeType.SELL)),
                tool.getModeChange(ModeType.SELL).getNextModeLore(tool.getCurrentMode(ModeType.SELL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.SELL).getChangePriceForMode(tool.getCurrentMode(ModeType.SELL))),
                getRelativeString(tool.getModeChange(ModeType.SELL).getChangePriceForMode(tool.getCurrentMode(ModeType.SELL)),
                        tool, "sell", level, tool.getModeChange(ModeType.SELL).getCurrency()),
                tool.getModeChange(ModeType.SELL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.SELL).getCurrency().getSuffix(),
                ToolsPlus.formatNumber(tool.getUses()));
        builder.addEnchantments(section.getStringList(condition + ".enchantments"));
        builder.addItemFlags(section.getStringList(condition + ".item-flags"));
        builder.addNBT(section.getBoolean(condition + ".unbreakable"));
        return builder.getItem();
    }

    public static boolean isConditionMet(ConfigurationSection section, PlayerTool tool, UpgradeType upgrade) {
        switch (section.getString("action").split(":")[1]) {
            case "upgrade":
                if (tool.getUpgradeLevel(upgrade) >= getConditionLevel(section)) {
                    return true;
                } else {
                    return false;
                }
//            case "permission":
//                return player.hasPermission(section.getString("action").split(":")[2]);
        }
        return true;
    }

    public static int getConditionLevel(ConfigurationSection section) {
        try {
            return Integer.parseInt(section.getString("action").split(":")[3]);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getCompoundPrice(PlayerTool tool, UpgradeType upgrade, ConfigurationSection section) {
        int current = tool.getUpgradeLevel(upgrade) + 1;
        double total = 0;
        while (current <= getConditionLevel(section)) {
            total += tool.getAbstractTool().getUpgrade(upgrade).getUpgradePriceForLevel(current);
            current++;
        }
        return total;
    }

    public static String getRelativeString(double amount, PlayerTool tool, String type, int condition, AbstractCurrency currency) {
        switch (type) {
            case "radius":
                if (!tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).isUpgradeable())
                    return Files.CONFIG.get().getString("not-upgradeable-placeholder");
                if (tool.getPeakUpgradeLevel(UpgradeType.RADIUS) > condition
                        || (tool.getPeakUpgradeLevel(UpgradeType.RADIUS) == condition && condition == tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel())
                        || tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(condition) == 0)
                    return Files.CONFIG.get().getString("free-placeholder");
                break;
            case "modifier":
                if (!tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).isUpgradeable())
                    return Files.CONFIG.get().getString("not-upgradeable-placeholder");
                if (tool.getPeakUpgradeLevel(UpgradeType.MODIFIER) > condition
                        || (tool.getPeakUpgradeLevel(UpgradeType.MODIFIER) == condition && condition == tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel())
                        || tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(condition) == 0)
                    return Files.CONFIG.get().getString("free-placeholder");
                break;
            case "tool":
                if (!tool.getAbstractTool().getModeChange(ModeType.TOOL).isChangingEnabled())
                    return Files.CONFIG.get().getString("no-mode-change-placeholder");
                if (tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL) + 1) == 0)
                    return Files.CONFIG.get().getString("free-placeholder");
                break;
            case "sell":
                if (!tool.getAbstractTool().getModeChange(ModeType.SELL).isChangingEnabled())
                    return Files.CONFIG.get().getString("no-mode-change-placeholder");
                if (tool.getModeChange(ModeType.SELL).getChangePriceForMode(tool.getCurrentMode(ModeType.SELL) + 1) == 0)
                    return Files.CONFIG.get().getString("free-placeholder");
                break;
        }
        return currency.getPrefix() + ToolsPlus.formatNumber(amount) + currency.getSuffix();
    }
}
