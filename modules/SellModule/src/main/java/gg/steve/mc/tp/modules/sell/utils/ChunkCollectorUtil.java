package gg.steve.mc.tp.modules.sell.utils;

import com.cloth.ChunkCollectorPlugin;
import com.cloth.collectors.ChunkCollector;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ChunkCollectorUtil {

    public static boolean isCollectorAtBlock(Location location) {
        if (ChunkCollectorPlugin.getInstance().getCollectorHandler().getCollectorAtLocation(location) == null)
            return false;
        ChunkCollector collector = ChunkCollectorPlugin.getInstance().getCollectorHandler().getCollectorAtLocation(location);
        return isSameBlock(collector.getLocation(), location);
    }

    public static double[] doSale(Collection<Location> collectors, Player player, PlayerTool tool, double[] vars) {
        if (vars[1] == 0) {
            if (CooldownToolAttribute.isCooldownActive(player, tool)) return vars;
        }
        if (collectors.isEmpty()) return vars;
        boolean hasSold = false;
        for (Location location : collectors) {
            ChunkCollector collector = ChunkCollectorPlugin.getInstance().getCollectorHandler().getCollectorAtLocation(location);
            for (Material content : collector.getInventory().getCollectedMaterials()) {
                if (collector.getAmountOf(content) <= 0) continue;
                if (!hasSold && vars[1] == 0 && tool.isOnCooldown(player)) return vars;
                hasSold = true;
                int amount = collector.getAmountOf(content);
                vars[0] += amount * collector.getInventory().getPrice(content);
                vars[1] += amount;
                collector.sell(content, player);
            }
        }
        return vars;
    }

    private static boolean isSameBlock(Location l1, Location l2) {
        Block b1 = l1.getBlock(), b2 = l2.getBlock();
        return b1.getX() == b2.getX() && b1.getY() == b2.getY() && b1.getZ() == b2.getZ();
    }
}
