package gg.steve.mc.tp.integration.region;

import gg.steve.mc.tp.integration.libs.ToolsPlusLibManager;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldGuardRegionProvider extends AbstractRegionProvider {
    private String version;

    public WorldGuardRegionProvider() {
        super(RegionProviderType.WORLDGUARD, "WorldGuard");
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            this.version = Bukkit.getPluginManager().getPlugin("WorldGuard").getDescription().getVersion();
        }
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        if (version.contains("7.")) {
            return ToolsPlusLibManager.getLibByType(ToolsPlusLibType.WORLDGUARD_v7).isBreakAllowed(player, block.getLocation());
        } else {
            return ToolsPlusLibManager.getLibByType(ToolsPlusLibType.WORLDGUARD_LEGACY).isBreakAllowed(player, block.getLocation());
        }
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        if (version.contains("7.")) {
            return ToolsPlusLibManager.getLibByType(ToolsPlusLibType.WORLDGUARD_v7).isBreakAllowed(player, location);
        } else {
            return ToolsPlusLibManager.getLibByType(ToolsPlusLibType.WORLDGUARD_LEGACY).isBreakAllowed(player, location);
        }
    }
}
