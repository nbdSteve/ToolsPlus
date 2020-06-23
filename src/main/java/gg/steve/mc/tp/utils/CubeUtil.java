package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CubeUtil {

    public static List<Block> getCube(Player player, Block start, int radius, String moduleId, boolean required) {
        List<Block> blockList = new ArrayList<>();
        // check if the block is whitelisted / blacklisted based on the module
        if (ToolConfigDataManager.queryMaterialList(moduleId, start.getType(), required)) {
            blockList.add(start);
        }
        if (radius <= 0) return blockList;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = start.getRelative(x, y, z);
                    if (block.getType().equals(Material.AIR)) continue;
                    boolean breakAllowed = true;
                    // check if the player can break this specific block
                    for (RegionProviderType regionProvider : RegionProviderType.values()) {
                        try {
                            if (!regionProvider.isBreakAllowed(player, block)) breakAllowed = false;
                        } catch (NoClassDefFoundError e) {
                            continue;
                        }
                    }
                    if (!breakAllowed) continue;
                    // check if the block is whitelisted / blacklisted based on the module
                    if (!ToolConfigDataManager.queryMaterialList(moduleId, block.getType(), required))
                        continue;
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }
}
