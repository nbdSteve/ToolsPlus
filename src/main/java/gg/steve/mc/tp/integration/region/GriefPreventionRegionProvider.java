package gg.steve.mc.tp.integration.region;

import gg.steve.mc.tp.framework.utils.LogUtil;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GriefPreventionRegionProvider extends AbstractRegionProvider {

    public GriefPreventionRegionProvider() {
        super(RegionProviderType.GRIEF_PREVENTION, "GriefPrevention");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(block.getLocation(), false, null);
        if (claim != null) {
            return claim.allowBreak(player, block.getType()) == null;
        }
        return true;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
        if (claim != null) {
            return claim.allowBreak(player, location.getBlock().getType()) == null;
        }
        return true;
    }
}
