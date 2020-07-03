package gg.steve.mc.tp.integration.region;

import gg.steve.mc.tp.integration.providers.FactionsProvider;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionsRegionProvider extends AbstractRegionProvider {

    public FactionsRegionProvider() {
        super(RegionProviderType.FACTIONS, "Factions");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        if (!FactionsProvider.isRunningFactions()) {
            LogUtil.warning("Tried to check factions region but the factions plugin is not registered, please report this to nbdSteve#0583 on discord.");
            return true;
        }
        return FactionsProvider.isBreakAllowed(player, block.getLocation());
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        if (!FactionsProvider.isRunningFactions()) {
            LogUtil.warning("Tried to check factions region but the factions plugin is not registered, please report this to nbdSteve#0583 on discord.");
            return true;
        }
        return FactionsProvider.isBreakAllowed(player, location);
    }
}
