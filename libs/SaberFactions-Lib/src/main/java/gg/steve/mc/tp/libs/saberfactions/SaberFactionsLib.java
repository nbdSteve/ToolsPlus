package gg.steve.mc.tp.libs.saberfactions;

import com.massivecraft.factions.*;
import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SaberFactionsLib extends ToolsPlusLib {

    public SaberFactionsLib() {
        super(ToolsPlusLibType.SABER_FACTIONS);
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (FPlayers.getInstance().getByPlayer(player).getFaction() == null) return false;
        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FLocation fLocation = new FLocation(location);
        return Board.getInstance().getFactionAt(fLocation).isWilderness() || faction == Board.getInstance().getFactionAt(fLocation);
    }

    @Override
    public boolean doTntDeposit(Player player, int i) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if (!fPlayer.hasFaction()) return false;
        Faction faction = fPlayer.getFaction();
        faction.addTnt(i);
        return true;
    }
}
