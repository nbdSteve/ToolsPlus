package gg.steve.mc.tp.upgrade.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeHelper {
    private int level, next;
    private double cost;
    private NBTItem item;
    private Player player;
    private PlayerTool tool;
    private AbstractUpgrade upgrade;

    public UpgradeHelper(Player player, PlayerTool tool, AbstractUpgrade upgrade) {
        this.player = player;
        this.tool = tool;
        this.upgrade = upgrade;
    }

    public boolean isUpgradeable() {
        this.item = new NBTItem(player.getItemInHand());
        if (!this.item.getItem().hasItemMeta() || this.item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to upgrade a tool that doesn't have any lore! Aborting.");
            return false;
        }
        this.level = this.tool.getUpgradeLevel(this.upgrade.getType());
        if (this.level == this.upgrade.getMaxLevel()) {
            GeneralMessage.TOOL_MAX_LEVEL.message(player);
            return false;
        }
        this.next = this.level + 1;
        this.cost = this.upgrade.getUpgradePriceForLevel(level);
        return true;
    }

    public boolean isUpgradeSuccess() {
        boolean peakAlreadySet = true;
        this.tool.setUpgradeLevel(this.upgrade.getType(), this.next);
        if (this.tool.getPeakUpgradeLevel(this.upgrade.getType()) < this.next) {
            this.tool.setPeakUpgradeLevel(this.upgrade.getType(), this.next);
            peakAlreadySet = false;
        }
        ItemStack updated = LoreUpdaterUtil.updateLore(this.item, this.upgrade.getType().getLowerCaseName() + "-upgrade-level", this.next,
                this.upgrade.getUpdateString().replace("{" + this.upgrade.getType().getLowerCaseName() + "-upgrade}", this.upgrade.getLoreStringForLevel(this.level)),
                this.upgrade.getUpdateString().replace("{" + this.upgrade.getType().getLowerCaseName() + "-upgrade}", this.upgrade.getLoreStringForLevel(this.next)),
                this.tool.getPeakUpgradeLevel(this.upgrade.getType()));
        if (GetToolHoldingUtil.isStillHoldingTool(this.tool.getToolId(), this.player.getItemInHand())) {
            this.player.setItemInHand(updated);
            this.player.updateInventory();
            if ((this.tool.getPeakUpgradeLevel(this.upgrade.getType()) > this.next
                    || this.tool.getPeakUpgradeLevel(upgrade.getType()) >=
                    this.tool.getAbstractTool().getUpgrade(upgrade.getType()).getMaxLevel()) && peakAlreadySet) {
                GeneralMessage.UPGRADE.message(this.player,
                        this.tool.getAbstractTool().getModule().getNiceName(),
                        ToolsPlus.formatNumber(this.next + 1),
                        ToolsPlus.formatNumber(this.upgrade.getMaxLevel() + 1),
                        ToolsPlus.formatNumber(0),
                        this.upgrade.getCurrency().getPrefix(),
                        this.upgrade.getCurrency().getSuffix());
            } else {
                GeneralMessage.UPGRADE.message(this.player,
                        this.tool.getAbstractTool().getModule().getNiceName(),
                        ToolsPlus.formatNumber(this.next + 1),
                        ToolsPlus.formatNumber(this.upgrade.getMaxLevel() + 1),
                        ToolsPlus.formatNumber(cost),
                        this.upgrade.getCurrency().getPrefix(),
                        this.upgrade.getCurrency().getSuffix());
            }
            return true;
        } else {
            LogUtil.warning("Upgrade dupe attempted by player: " + this.player.getName() + ", Tools+ has stopped the tool action from happening.");
            ToolsPlus.eco().depositPlayer(this.player, this.cost);
            return false;
        }
    }

    public boolean hasAlreadyPayedForLevel() {
        return this.tool.getPeakUpgradeLevel(this.upgrade.getType()) >= this.next;
    }

    public double getCost() {
        return cost;
    }
}
