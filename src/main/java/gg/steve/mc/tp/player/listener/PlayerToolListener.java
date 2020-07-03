package gg.steve.mc.tp.player.listener;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.attribute.types.OmniToolAttribute;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.ToolPlayer;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerToolListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        try {
            player.getPlayerTool().getCurrentModeData().onBlockBreak(event, player.getPlayerTool());
        } catch (Exception e) {
            player.getPlayerTool().getAbstractTool().getData().onBlockBreak(event, player.getPlayerTool());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void omniProc(BlockDamageEvent event) {
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        if (!player.getPlayerTool().getAbstractTool().getAttributeManager().isAttributeEnabled(ToolAttributeType.OMNI))
            return;
        OmniToolAttribute.changeToolType(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void interact(PlayerInteractEvent event) {
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        try {
            player.getPlayerTool().getCurrentModeData().onInteract(event, player.getPlayerTool());
        } catch (Exception e) {
            player.getPlayerTool().getAbstractTool().getData().onInteract(event, player.getPlayerTool());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void sneakSwitch(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;
        Player player = event.getPlayer();
        if (!PlayerToolManager.isHoldingTool(player.getUniqueId())) return;
        PlayerTool tool = PlayerToolManager.getToolPlayer(player.getUniqueId()).getPlayerTool();
        if (player.isSneaking()) {
            if (tool.getModeChange(ModeType.TOOL).isSneakSwitch()) {
                event.setCancelled(true);
                tool.getModeChange(ModeType.TOOL).changeMode(player, tool);
                return;
            }
            if (tool.getModeChange(ModeType.SELL).isSneakSwitch()) {
                event.setCancelled(true);
                tool.getModeChange(ModeType.SELL).changeMode(player, tool);
            }
        } else {
            if (tool.getModeChange(ModeType.TOOL).isRightClickSwitch()) {
                event.setCancelled(true);
                tool.getModeChange(ModeType.TOOL).changeMode(player, tool);
                return;
            }
            if (tool.getModeChange(ModeType.SELL).isRightClickSwitch()) {
                event.setCancelled(true);
                tool.getModeChange(ModeType.SELL).changeMode(player, tool);
            }
        }
    }
}