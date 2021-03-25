package gg.steve.mc.tp.integration.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GuildsRegionProvider extends AbstractRegionProvider {
//    private GuildsAPI guilds = Guilds.getApi();

    public GuildsRegionProvider() {
        super(RegionProviderType.GUILDS, "Guilds");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
//        GuildHandler handler = guilds.getGuildHandler();
//        if (guilds.getGuildHandler().check)
        return true;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        return true;
    }
}
