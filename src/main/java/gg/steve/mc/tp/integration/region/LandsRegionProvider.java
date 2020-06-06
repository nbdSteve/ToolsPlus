package gg.steve.mc.tp.integration.region;

import gg.steve.mc.tp.ToolsPlus;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.LandArea;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LandsRegionProvider extends AbstractRegionProvider {
    private LandsIntegration lands;

    public LandsRegionProvider() {
        super(RegionProviderType.LANDS, "Lands");
        if (isEnabled()) lands = new LandsIntegration(ToolsPlus.get());
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        if (lands == null) lands = new LandsIntegration(ToolsPlus.get());
        LandArea land = lands.getArea(block.getLocation());
        if (land == null) return true;
        return land.isTrusted(player.getUniqueId());
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        if (lands == null) lands = new LandsIntegration(ToolsPlus.get());
        LandArea land = lands.getArea(location);
        if (land == null) return true;
        return land.isTrusted(player.getUniqueId());
    }
}
