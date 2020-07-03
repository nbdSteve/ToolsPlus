package gg.steve.mc.tp.libs.factionsx;

import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import net.prosavage.factionsx.manager.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionsXLib extends ToolsPlusLib {

    public FactionsXLib() {
        super(ToolsPlusLibType.FACTIONS_X);
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        return PlayerManager.INSTANCE.getFPlayer(player).canBuildAt(location);
    }

    @Override
    public boolean doTntDeposit(Player player, int i) {
        return false;
    }
}
