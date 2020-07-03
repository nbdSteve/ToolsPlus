package gg.steve.mc.tp.integration.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldBorderRegionProvider extends AbstractRegionProvider {

    public WorldBorderRegionProvider() {
        super(RegionProviderType.WORLD_BORDER, "ToolsPlus");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        Location blockLocation = block.getLocation().add(0.5, 0, 0.5);
        double x = blockLocation.getX();
        double z = blockLocation.getZ();
        double size = block.getWorld().getWorldBorder().getSize()/2;
        Location center = block.getWorld().getWorldBorder().getCenter();
        return !((x >= center.clone().add(size, 0, 0).getX() || z >= center.clone().add(0, 0, size).getZ()) || (x <= center.clone().subtract(size,0,0).getX() || (z <= center.clone().subtract(0,0, size).getZ())));
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        Location blockLocation = location.add(0.5, 0, 0.5);
        double x = blockLocation.getX();
        double z = blockLocation.getZ();
        double size = location.getWorld().getWorldBorder().getSize()/2;
        Location center = location.getWorld().getWorldBorder().getCenter();
        return !((x >= center.clone().add(size, 0, 0).getX() || z >= center.clone().add(0, 0, size).getZ()) || (x <= center.clone().subtract(size,0,0).getX() || (z <= center.clone().subtract(0,0, size).getZ())));
    }
}
