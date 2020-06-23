package gg.steve.mc.tp.integration.libs;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class ToolsPlusLib {
    private ToolsPlusLibType libType;

    public ToolsPlusLib(ToolsPlusLibType libType) {
        this.libType = libType;
    }

    public ToolsPlusLibType getLibType() {
        return libType;
    }

    public abstract boolean isBreakAllowed(Player player, Location location);

    public abstract boolean doTntDeposit(Player player, int amount);
}