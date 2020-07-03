package gg.steve.mc.tp.player;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerToolManager implements Listener {
    private static Map<UUID, ToolPlayer> playerToolMap;

    public PlayerToolManager() {
    }

    public static void initialise() {
        playerToolMap = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getItemInHand().getType() == Material.AIR) break;
            NBTItem item = new NBTItem(player.getItemInHand());
            if (!GetToolHoldingUtil.isHoldingTool(item)) break;
            addToolPlayer(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(item));
        }
    }

    public static void shutdown() {
        if (playerToolMap != null && !playerToolMap.isEmpty()) playerToolMap.clear();
    }

    public static boolean addToolPlayer(UUID playerId, PlayerTool tool) {
        if (playerToolMap.containsKey(playerId)) return false;
        return playerToolMap.put(playerId, new ToolPlayer(playerId, tool)) != null;
    }

    public static boolean removeToolPlayer(UUID playerId) {
        if (!playerToolMap.containsKey(playerId)) return false;
        return playerToolMap.remove(playerId) != null;
    }

    public static void updateToolType(UUID playerId, PlayerTool tool) {
        if (!playerToolMap.containsKey(playerId)) {
            addToolPlayer(playerId, tool);
        } else {
            playerToolMap.get(playerId).setTool(tool);
        }
    }

    public static boolean isHoldingTool(UUID playerId) {
        if (!playerToolMap.containsKey(playerId)) return false;
        return playerToolMap.get(playerId) != null;
    }

    public static ToolPlayer getToolPlayer(UUID playerId) {
        if (!playerToolMap.containsKey(playerId)) return null;
        return playerToolMap.get(playerId);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.AIR) return;
        NBTItem item = new NBTItem(player.getItemInHand());
        if (!GetToolHoldingUtil.isHoldingTool(item)) return;
        addToolPlayer(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(item));
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        removeToolPlayer(event.getPlayer().getUniqueId());
    }
}
