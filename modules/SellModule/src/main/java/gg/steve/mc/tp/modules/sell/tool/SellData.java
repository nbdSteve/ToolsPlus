package gg.steve.mc.tp.modules.sell.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.framework.utils.CubeUtil;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.modules.sell.SellModule;
import gg.steve.mc.tp.modules.sell.utils.ChunkCollectorUtil;
import gg.steve.mc.tp.modules.sell.utils.ContainerSaleUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent event, PlayerTool tool) {
    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        List<Block> blocks = CubeUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius(), SellModule.moduleId, true);
        if (blocks.isEmpty()) return;
        List<Inventory> inventories = new ArrayList<>();
        Map<Chunk, Location> collectors = new HashMap<>();
        for (Block block : blocks) {
            if (isUsingCollectors()
                    && ChunkCollectorUtil.isCollectorAtBlock(block.getLocation())
                    && !collectors.containsKey(block.getChunk())) {
                collectors.put(block.getChunk(), block.getLocation());
            }
            if (!(block.getState() instanceof InventoryHolder)) continue;
            Inventory inventory = ((InventoryHolder) block.getState()).getInventory();
            if (!inventories.contains(inventory)) inventories.add(inventory);
        }
        if (inventories.isEmpty() && collectors.isEmpty()) return;
        double[] vars = ContainerSaleUtil.doInventorySale(event.getPlayer(), inventories, tool);
        if (isUsingCollectors()) {
            ChunkCollectorUtil.doSale(collectors.values(), event.getPlayer(), tool, vars);
        }
        if (vars[0] > 0) {
            GeneralMessage.SALE.message(event.getPlayer(),
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(vars[1]),
                    ToolsPlus.formatNumber(vars[0]));
        } else {
            if (!CooldownToolAttribute.isCooldownActive(event.getPlayer(), tool)) {
                GeneralMessage.NO_SALE.message(event.getPlayer(), tool.getAbstractTool().getModule().getNiceName());
            }
        }
        if (vars[1] == 0) return;
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), (int) vars[1])) return;
    }

    private boolean isUsingCollectors() {
        return Bukkit.getPluginManager().getPlugin("ChunkCollectors") != null;
    }
}
