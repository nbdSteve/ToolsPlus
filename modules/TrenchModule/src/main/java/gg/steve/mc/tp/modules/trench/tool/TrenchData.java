package gg.steve.mc.tp.modules.trench.tool;

import gg.steve.mc.tp.integration.sell.SellIntegrationManager;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.modules.trench.TrenchModule;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.utils.CubeUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TrenchData implements ToolData {

    public TrenchData() {
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, PlayerTool tool) {
        List<Block> blocks = CubeUtil.getCube(event.getPlayer(), event.getBlock(), tool.getRadius(), TrenchModule.moduleId, false);
        if (!blocks.contains(event.getBlock())) event.setCancelled(true);
        if (blocks.isEmpty()) return;
        if (tool.isOnCooldown(event.getPlayer())) return;
        if (!tool.decrementUses(event.getPlayer())) return;
        boolean full = event.getPlayer().getInventory().firstEmpty() == -1,
                autoSell = tool.getModeChange(ModeType.SELL).getCurrentModeString(tool.getCurrentMode(ModeType.SELL)).equalsIgnoreCase("sell"),
                silk = event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH),
                playersGetDrops = tool.getAbstractTool().isPlayersGetDrops();
        if (full) {
            GeneralMessage.INVENTORY_FULL.message(event.getPlayer());
        }
        if (autoSell) {
            SellIntegrationManager.doBlockSale(event.getPlayer(), blocks, tool, silk, true);
        } else {
            for (Block block : blocks) {
                // if the player is using silk touch give them items accordingly, adds items straight to inventory
                if (playersGetDrops) {
                    if (silk) {
                        event.getPlayer().getInventory().addItem(new ItemStack(block.getType(), 1, block.getData()));
                    } else {
                        for (ItemStack item : block.getDrops(event.getPlayer().getItemInHand())) {
                            event.getPlayer().getInventory().addItem(item);
                        }
                    }
                }
                // clear drops and remove the block
                block.getDrops().clear();
                block.setType(Material.AIR);
            }
        }
        if (!tool.incrementBlocksMined(event.getPlayer(), blocks.size() - 1)) return;
    }

    @Override
    public void onInteract(PlayerInteractEvent playerInteractEvent, PlayerTool playerTool) {

    }
}