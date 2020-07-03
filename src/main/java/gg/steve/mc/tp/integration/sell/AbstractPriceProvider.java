package gg.steve.mc.tp.integration.sell;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractPriceProvider {
    private String plugin;

    public AbstractPriceProvider(String plugin) {
        this.plugin = plugin;
    }

    public boolean isEnabled() {
        if (this.plugin.equalsIgnoreCase("")) return true;
        return Bukkit.getPluginManager().getPlugin(this.plugin) != null;
    }

    public abstract double getItemPrice(Player player, ItemStack item);

    public abstract void shutdown();
}
