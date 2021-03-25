package gg.steve.mc.tp.modules.sell.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.integration.sell.SellIntegrationManager;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ContainerSaleUtil {

    public static double[] doInventorySale(Player player, List<Inventory> inventories, PlayerTool tool) {
        // vars[0] = deposit, vars[1] = amount
        double[] vars = new double[2];
        vars[0] = 0;
        vars[1] = 0;
        boolean hasSold = false;
        if (CooldownToolAttribute.isCooldownActive(player, tool)) return vars;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.AIR))
                    continue;
                ItemStack item = inventory.getItem(slot);
                if (item.hasItemMeta()) continue;
                if (!hasSold && (SellIntegrationManager.getItemPrice(player, item) <= 0)) continue;
                if (!hasSold && tool.isOnCooldown(player)) return vars;
                double sale;
                if ((sale = SellIntegrationManager.sellItem(player, item, tool)) <= 0) continue;
                hasSold = true;
                inventory.clear(slot);
                vars[0] += sale;
                vars[1] += item.getAmount();
            }
        }
        return vars;
    }
}
