package gg.steve.mc.tp.integration.sell;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellIntegrationManager {
    private static List<PriceProviderType> providerHierarchy;

    public static void initialiseProviderHierarchy() {
        providerHierarchy = new ArrayList<>();
        for (String provider : Files.CONFIG.get().getStringList("price-provider-hierarchy")) {
            providerHierarchy.add(PriceProviderType.getProvider(provider));
        }
        if (providerHierarchy.isEmpty()) providerHierarchy.add(PriceProviderType.INTERNAL);
    }

    public static void shutdown() {
        if (providerHierarchy != null && !providerHierarchy.isEmpty()) providerHierarchy.clear();
    }

    public static void doBlockSale(Player player, List<Block> blocks, PlayerTool tool, boolean silk, boolean clear) {
        double deposit = 0, amount = 0;
        Map<Material, Map<Byte, Integer>> saleCache = new HashMap<>();
        for (Block block : blocks) {
            if (silk) {
                if (saleCache.containsKey(block.getType())) {
                    if (saleCache.get(block.getType()).containsKey(block.getData())) {
                        saleCache.get(block.getType()).put(block.getData(), saleCache.get(block.getType()).get(block.getData()) + 1);
                    } else {
                        saleCache.get(block.getType()).put(block.getData(), 1);
                    }
                } else {
                    saleCache.put(block.getType(), new HashMap<>());
                    saleCache.get(block.getType()).put(block.getData(), 1);
                }
            } else {
                for (ItemStack item : block.getDrops(player.getItemInHand())) {
                    if (saleCache.containsKey(item.getType())) {
                        if (saleCache.get(item.getType()).containsKey((byte) item.getDurability())) {
                            saleCache.get(item.getType()).put((byte) item.getDurability(), saleCache.get(item.getType()).get((byte) item.getDurability()) + 1);
                        } else {
                            saleCache.get(item.getType()).put((byte) item.getDurability(), 1);
                        }
                    } else {
                        saleCache.put(item.getType(), new HashMap<>());
                        saleCache.get(item.getType()).put((byte) item.getDurability(), 1);
                    }
                }
            }
            if (clear) {
                block.getDrops().clear();
                block.setType(Material.AIR);
            }
        }
        for (Material material : saleCache.keySet()) {
            for (Byte data : saleCache.get(material).keySet()) {
                int size = saleCache.get(material).get(data);
                double sale;
                if ((sale = sellItem(player, material, data, size, tool)) > 0) {
                    deposit += sale;
                    amount += size;
                } else {
                    player.getInventory().addItem(new ItemStack(material, size, data));
                }
            }
        }
        if (deposit > 0) {
            GeneralMessage.SALE.message(player,
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(deposit));
        }
    }

    public static double sellItem(Player player, Material material, Byte data, int amount, PlayerTool tool) {
        if (ToolsPlus.eco() == null) {
            LogUtil.warning("Tried to auto sell an item for " + player.getName() + ", but there is no economy loaded.");
            return 0;
        }
        double price = getItemPrice(player, material, data) * amount * tool.getModifier();
        ToolsPlus.eco().depositPlayer(player, price);
        return price;
    }

    public static double sellItem(Player player, ItemStack item, PlayerTool tool) {
        if (ToolsPlus.eco() == null) {
            LogUtil.warning("Tried to auto sell an item for " + player.getName() + ", but there is no economy loaded.");
            return 0;
        }
        double price = getItemPrice(player, item.getType(), (byte) item.getDurability()) * item.getAmount() * tool.getModifier();
        ToolsPlus.eco().depositPlayer(player, price);
        return price;
    }

    public static double getItemPrice(Player player, Material material, Byte data) {
        return getItemPrice(player, new ItemStack(material, 1, data));
    }

    public static double getItemPrice(Player player, ItemStack item) {
        for (int i = 0; i <= providerHierarchy.size(); i++) {
            switch (providerHierarchy.get(i)) {
                case SHOP_GUI_PLUS:
                    if (!PriceProviderType.SHOP_GUI_PLUS.isEnabled()) continue;
                    double price = PriceProviderType.SHOP_GUI_PLUS.getPriceProvider().getItemPrice(player, item);
                    if (price > 0) return price;
                    continue;
                case ESSENTIALS:
                    if (!PriceProviderType.ESSENTIALS.isEnabled()) continue;
                    price = PriceProviderType.ESSENTIALS.getPriceProvider().getItemPrice(player, item);
                    if (price > 0) return price;
                    continue;
                case INTERNAL:
                    return PriceProviderType.INTERNAL.getPriceProvider().getItemPrice(player, item);
            }
        }
        return 0;
    }
}
