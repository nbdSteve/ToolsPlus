package gg.steve.mc.tp.integration.sell.providers;

import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.integration.sell.AbstractPriceProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InternalPriceProvider extends AbstractPriceProvider {
    private Map<String, Double> priceMap;

    public InternalPriceProvider(String plugin) {
        super(plugin);
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

    @Override
    public double getItemPrice(Player player, ItemStack item) {
        String material = item.getType().toString() + ":" + item.getDurability();
        if (priceMap.containsKey(material)) return priceMap.get(material);
        return 0;
    }

    @Override
    public void shutdown() {
        if (priceMap != null && !priceMap.isEmpty()) priceMap.clear();
    }
}
