package gg.steve.mc.tp.libs.massivefactions;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MassiveFactionsLib extends ToolsPlusLib {

    public MassiveFactionsLib() {
        super(ToolsPlusLibType.MASSIVE_FACTIONS);
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (Bukkit.getPluginManager().getPlugin("Factions") == null) return true;
        MPlayer mPlayer = MPlayer.get(player);
        return BoardColl.get().getFactionAt(PS.valueOf(location)).getName().equalsIgnoreCase("Wilderness")
                || BoardColl.get().getFactionAt(PS.valueOf(location)).equals(mPlayer.getFaction());
    }

    @Override
    public boolean doTntDeposit(Player player, int i) {
        return false;
    }
}
