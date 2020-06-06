package gg.steve.mc.tp.integration.sell;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InternalPriceProvider {
    private static Map<String, Double> priceMap;

    public static void loadPriceMap() {
        priceMap = new HashMap<>();
        for (String entry : Files.CONFIG.get().getStringList("price-map")) {
            String[] parts = entry.split(":");
            try {
                Material.getMaterial(parts[0].toUpperCase());
            } catch (Exception e) {
                LogUtil.warning("Could not parse " + parts[0].toUpperCase() + " to a bukkit material while, please check your syntax");
                continue;
            }
            priceMap.put(parts[0].toUpperCase() + ":" + parts[1], Double.parseDouble(parts[2]));
        }
    }

    public static void shutdown() {
        if (priceMap != null && !priceMap.isEmpty()) priceMap.clear();
    }

    public static double getPrice(ItemStack item) {
        String material = item.getType().toString() + ":" + item.getDurability();
        if (priceMap.containsKey(material)) return priceMap.get(material);
        return 0;
    }
}
