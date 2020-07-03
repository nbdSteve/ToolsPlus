package gg.steve.mc.tp.mode.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.mode.AbstractModeChange;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolTypeModeChange extends AbstractModeChange {

    public ToolTypeModeChange(PluginFile file) {
        super(ModeType.TOOL, file);
    }

    @Override
    public boolean changeMode(Player player, PlayerTool tool) {
        NBTItem item = new NBTItem(player.getItemInHand());
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to switch mode for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        int current = tool.getCurrentMode(getType());
        double cost = getChangePriceForMode(current);
        int next = getNextMode(current);
        if (!getCurrency().isSufficientFunds(player, tool, getChangePriceForMode(current))) return false;
        tool.setModeLevel(getType(), next);
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "tool-mode-level", next,
                getUpdateString().replace("{tool-mode}", (String) getTrack().get(current).get(2)),
                getUpdateString().replace("{tool-mode}", (String) getTrack().get(next).get(2)));
        if (GetToolHoldingUtil.isStillHoldingTool(tool.getToolId(), player.getItemInHand())) {
            player.setItemInHand(updated);
            player.updateInventory();
            GeneralMessage.MODE_CHANGE.message(player,
                    (String) getTrack().get(next).get(2),
                    ToolsPlus.formatNumber(cost),
                    getCurrency().getPrefix(),
                    getCurrency().getSuffix());
            return true;
        } else {
            LogUtil.warning("Mode switch dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }
}
