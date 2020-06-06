package gg.steve.mc.tp.integration.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class AbstractRegionProvider {
    private RegionProviderType provider;
    private String pluginName;

    public AbstractRegionProvider(RegionProviderType provider, String pluginName) {
        this.provider = provider;
        this.pluginName = pluginName;
    }

    public RegionProviderType getProvider() {
        return provider;
    }

    public boolean isEnabled() {
        return Bukkit.getPluginManager().getPlugin(this.pluginName) != null;
    }

    public abstract boolean isBreakAllowed(Player player, Block block);

    public abstract boolean isBreakAllowed(Player player, Location location);
}
