package gg.steve.mc.tp;

import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.ToolPlayer;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Api methods for the Tools+ Minecraft plugin.
 */
public class ToolsPlusApi {

    /**
     * Returns the main instance of the plugin.
     *
     * @return ToolsPlus
     */
    public static ToolsPlus getPluginInstance() {
        return ToolsPlus.get();
    }

    /**
     * Returns true if the player is holding a Tools+ tool, false otherwise.
     *
     * @param player Player, player to query
     * @return boolean
     */
    public static boolean isHoldingTool(Player player) {
        return PlayerToolManager.isHoldingTool(player.getUniqueId());
    }

    /**
     * Returns ToolPlayer instance for the player, returns null if there isn't a
     * ToolPlayer object for the player.
     *
     * @param player Player, player to query
     * @return ToolPlayer
     */
    public static ToolPlayer getToolPlayer(Player player) {
        if (!PlayerToolManager.isHoldingTool(player.getUniqueId())) return null;
        return PlayerToolManager.getToolPlayer(player.getUniqueId());
    }

    /**
     * Returns PlayerTool object for the specified player, this is the tool the player
     * is currently holding. Returns null if the player isn't holding a Tools+ tool.
     *
     * @param player Player, player to query
     * @return PlayerTool
     */
    public static PlayerTool getPlayerTool(Player player) {
        if (!PlayerToolManager.isHoldingTool(player.getUniqueId())) return null;
        return PlayerToolManager.getToolPlayer(player.getUniqueId()).getPlayerTool();
    }

    /**
     * Returns the installed tool module with the specified identifier. Returns
     * null if the module is uninstalled or not in the modules folder.
     *
     * @param identifier String, the identifier for the module
     * @return ToolsPlusModule
     */
    public static ToolsPlusModule getInstalledModule(String identifier) {
        if (!ModuleManager.isInstalled(identifier)) return null;
        return ModuleManager.getInstalledModule(identifier);
    }

    /**
     * Returns the installed tool modules as a collection. If there are no
     * modules installed an empty list will be returned.
     *
     * @return Collection<ToolsPlusModule>
     */
    public static Collection<ToolsPlusModule> getInstalledModules() {
        return ModuleManager.getInstalledModules();
    }
}
