package gg.steve.mc.tp.tool;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ToolData {

    void onBlockBreak(BlockBreakEvent event, PlayerTool tool);

    void onInteract(PlayerInteractEvent event, PlayerTool tool);
}
