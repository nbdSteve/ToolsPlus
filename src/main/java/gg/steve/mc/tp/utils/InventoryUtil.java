package gg.steve.mc.tp.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryUtil {

    public static void addBlockToInventory(Block block, UUID playerId) {
        Player player;
        try {
            player = Bukkit.getPlayer(playerId);
        } catch (Exception e) {
            //player not online
            return;
        }
        if (player.getInventory().firstEmpty() == -1) return;
        for (ItemStack item : block.getDrops()) {
            player.getInventory().addItem(item);
        }
    }
}
