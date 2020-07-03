package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BlocksMinedToolAttribute extends AbstractToolAttribute {

    public BlocksMinedToolAttribute(String updateString) {
        super(ToolAttributeType.BLOCKS_MINED, updateString);
    }

    @Override
    public boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost) {
        return false;
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to increment blocks mined for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "blocks", current + change,
                getUpdateString().replace("{mined}", ToolsPlus.formatNumber(current)),
                getUpdateString().replace("{mined}", ToolsPlus.formatNumber(current + change)));
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(updated);
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Blocks mined dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    @Override
    public boolean isOnCooldown(Player player, PlayerTool tool) {
        return false;
    }
}