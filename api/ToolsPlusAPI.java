package dev.nuer.tp.api;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.entity.Player;

/**
 * Class that handles API calls for the plugin
 */
public class ToolsPlusAPI {

    /**
     * Returns the ToolsPlus instance
     *
     * @return ToolsPlus
     */
    public static ToolsPlus getInstance() {
        return ToolsPlus.getPlugin(ToolsPlus.class);
    }

    /**
     * Returns true if the plugin is enabled
     *
     * @return boolean
     */
    public static boolean isEnabled() {
        if (getInstance().isEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the player is on the respective tool cooldown
     *
     * @param player           Player, the player to check
     * @param cooldownToolType String, tooltype - lightning, sell, tnt and sand are valid
     * @return boolean
     */
    public static boolean isOnToolCooldown(Player player, String cooldownToolType) {
        return PlayerToolCooldown.getCooldownMap(cooldownToolType).get(player.getUniqueId()) != null;
    }

    /**
     * Gets the number of seconds remaining for a players cooldown
     *
     * @param player           Player, the player to check
     * @param cooldownToolType String, tooltype - lightning, sell, tnt and sand are valid
     * @return Integer
     */
    public static int getToolCooldown(Player player, String cooldownToolType) {
        if (isOnToolCooldown(player, cooldownToolType)) {
            return PlayerToolCooldown.getCooldownMap(cooldownToolType).get(player.getUniqueId());
        }
        return 0;
    }
}
