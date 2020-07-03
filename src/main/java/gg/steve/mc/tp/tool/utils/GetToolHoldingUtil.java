package gg.steve.mc.tp.tool.utils;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GetToolHoldingUtil {

    public static PlayerTool getHoldingTool(NBTItem item) {
        UUID toolId = UUID.fromString(item.getString("tools+.uuid"));
        return ToolsManager.getPlayerTool(toolId);
    }

    public static boolean isHoldingTool(NBTItem item) {
        if (item.getString("tools+.uuid").equalsIgnoreCase("")) return false;
        UUID toolId = UUID.fromString(item.getString("tools+.uuid"));
        if (!ToolsManager.isPlayerToolRegistered(toolId)) ToolsManager.addPlayerTool(toolId, item);
        return true;
    }

    public static boolean isStillHoldingTool(UUID toolId, ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getString("tools+.uuid").equalsIgnoreCase("")) return false;
        return nbtItem.getString("tools+.uuid").equalsIgnoreCase(String.valueOf(toolId));
    }
}
