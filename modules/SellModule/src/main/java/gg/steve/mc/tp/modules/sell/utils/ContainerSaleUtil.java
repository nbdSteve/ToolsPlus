package gg.steve.mc.tp.modules.sell.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.integration.sell.SellIntegrationManager;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ContainerSaleUtil {

    public static int doInventorySale(Player player, List<Inventory> inventories, PlayerTool tool) {
        int deposit = 0, amount = 0;
        boolean hasSold = false;
        if (CooldownToolAttribute.isCooldownActive(player, tool)) return 0;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.AIR))
                    continue;
                ItemStack item = inventory.getItem(slot);
                if (item.hasItemMeta()) continue;
                if (!hasSold && (SellIntegrationManager.getItemPrice(player, item) <= 0)) continue;
                if (!hasSold && tool.isOnCooldown(player)) return 0;
                double sale;
                if ((sale = SellIntegrationManager.sellItem(player, item, tool)) <= 0) continue;
                hasSold = true;
                inventory.clear(slot);
                deposit += sale;
                amount += item.getAmount();
            }
        }
        if (deposit > 0) {
            GeneralMessage.SALE.message(player,
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(deposit));
        } else {
            if (!CooldownToolAttribute.isCooldownActive(player, tool))
                GeneralMessage.NO_SALE.message(player, tool.getAbstractTool().getModule().getNiceName());
        }
        return amount;
    }
}
