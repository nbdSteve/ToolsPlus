package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class UsesToolAttribute extends AbstractToolAttribute {

    public UsesToolAttribute(String updateString) {
        super(ToolAttributeType.USES, updateString);
    }

    @Override
    public boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost) {
        NBTItem item = new NBTItem(player.getItemInHand());
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to increases uses for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        if (!currency.isSufficientFunds(player, tool, cost)) return false;
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "uses", tool.getUses() + amount,
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(tool.getUses())),
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(tool.getUses() + amount)));
        if (GetToolHoldingUtil.isStillHoldingTool(tool.getToolId(), player.getItemInHand())) {
            tool.setUses(tool.getUses() + amount);
            player.setItemInHand(updated);
            player.updateInventory();
            GeneralMessage.USES_PURCHASE.message(player,
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(cost),
                    currency.getPrefix(),
                    currency.getSuffix());
            return true;
        } else {
            LogUtil.warning("Uses dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to decrement uses for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        if (current + change < 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
            GeneralMessage.OUT_OF_USES.message(player, ToolsManager.getPlayerTool(toolId).getAbstractTool().getModule().getNiceName());
            ToolsManager.removePlayerTool(toolId);
            PlayerToolManager.removeToolPlayer(player.getUniqueId());
            return true;
        }
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "uses", current + change,
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current)),
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current + change)));
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(updated);
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Uses dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    @Override
    public boolean isOnCooldown(Player player, PlayerTool tool) {
        return false;
    }
}