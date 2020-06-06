package gg.steve.mc.tp.integration.region;

import net.prosavage.factionsx.manager.PlayerManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionsXRegionProvider extends AbstractRegionProvider {

    public FactionsXRegionProvider() {
        super(RegionProviderType.FACTIONS_X, "FactionsX");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        return PlayerManager.INSTANCE.getFPlayer(player).canBuildAt(block.getLocation());
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        return PlayerManager.INSTANCE.getFPlayer(player).canBuildAt(location);
    }
}
