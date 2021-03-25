package gg.steve.mc.tp.integration.providers;

import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibManager;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FactionsProvider {
    private static ToolsPlusLibType factionsPlugin;

    public static void loadProvider() {
        factionsPlugin = null;
        if (Bukkit.getPluginManager().getPlugin("FactionsX") != null) {
            factionsPlugin = ToolsPlusLibType.FACTIONS_X;
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("Factions") == null) return;
        Plugin factions = Bukkit.getPluginManager().getPlugin("Factions");
        if (factions.getDescription().getMain().contains("FactionsPlugin")
                || factions.getDescription().getMain().equalsIgnoreCase("com.massivecraft.factions.P")) {
            factionsPlugin = ToolsPlusLibType.SABER_FACTIONS;
        } else if (factions.getDescription().getWebsite().contains("prosavage.net")) {
            factionsPlugin = ToolsPlusLibType.SAVAGE_FACTIONS;
        } else if (factions.getDescription().getWebsite().contains("massivecraft.com")) {
            factionsPlugin = ToolsPlusLibType.MASSIVE_FACTIONS;
        } else if (factions.getDescription().getWebsite().contains("factions.support")
        || factions.getDescription().getWebsite().contains("factionsuuid.1035")) {
            factionsPlugin = ToolsPlusLibType.FACTIONS_UUID;
        }
    }

    public static boolean isRunningFactions() {
        return factionsPlugin != null;
    }

    public static ToolsPlusLib getProvider() {
        return ToolsPlusLibManager.getLibByType(factionsPlugin);
    }

    public static boolean isBreakAllowed(Player player, Location location) {
        return getProvider().isBreakAllowed(player, location);
    }

    public static boolean doTntDeposit(Player player, int amount) {
        return getProvider().doTntDeposit(player, amount);
    }
}
