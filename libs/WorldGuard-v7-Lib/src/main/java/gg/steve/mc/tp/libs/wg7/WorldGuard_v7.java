package gg.steve.mc.tp.libs.wg7;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuard_v7 extends ToolsPlusLib {

    public WorldGuard_v7() {
        super(ToolsPlusLibType.WORLDGUARD_v7);
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        BukkitWorld world = new BukkitWorld(location.getWorld());
        BlockVector3 v = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        try {
            ApplicableRegionSet set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world).getApplicableRegions(v);
            return set.queryState(null, Flags.BLOCK_BREAK) != StateFlag.State.DENY;
        } catch (NullPointerException e) {
            return true;
        }
    }

    @Override
    public boolean doTntDeposit(Player player, int i) {
        return false;
    }
}
