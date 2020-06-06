package gg.steve.mc.tp.integration.region;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldGuardRegionProvider extends AbstractRegionProvider {

    public WorldGuardRegionProvider() {
        super(RegionProviderType.WORLDGUARD, "WorldGuard");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(block.getLocation().getWorld()).getApplicableRegions(block.getLocation());
        return set.queryState(null, DefaultFlag.BLOCK_BREAK) != StateFlag.State.DENY;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(location.getWorld()).getApplicableRegions(location);
        return set.queryState(null, DefaultFlag.BLOCK_BREAK) != StateFlag.State.DENY;
    }
}
