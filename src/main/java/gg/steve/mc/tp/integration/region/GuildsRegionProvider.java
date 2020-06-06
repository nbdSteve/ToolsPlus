package gg.steve.mc.tp.integration.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GuildsRegionProvider extends AbstractRegionProvider {
    public GuildsRegionProvider(RegionProviderType provider, String pluginName) {
        super(provider, pluginName);
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        return false;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        return false;
    }
//    private GuildsAPI guilds = Guilds.getApi();

//    public GuildsRegionProvider() {
//        super(RegionProviderType.FA, "Guilds");
//    }
}
