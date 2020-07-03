package gg.steve.mc.tp.framework.message;

import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum DebugMessage {
    // module
    RELOAD_ALL_MODULES("reload-all-modules", "{module-count}"),
    RELOAD_MODULE("reload-module", "{module}"),
    LIST_MODULES("list-modules", "{modules-number}", "{modules-list}"),
    MODULE_INFO("module-info", "{module}", "{author}", "{version}", "{identifier}", "{tool-amount}"),
    MODULE_NOT_INSTALLED("module-not-installed"),
    MODULE_ALREADY_INSTALLED("module-already-installed"),
    INVALID_MODULE("invalid-module"),
    MODULE_INSTALLED("module-installed", "{module}"),
    MODULE_UN_INSTALLED("module-un-installed", "{module}"),
    // tools
    LIST_TOOLS("list-tools", "{tools-number}", "{tools-list}", "{player-tools-number}"),
    PLAYER_TOOL_INFO("player-tool-info", "{target}", "{tool-uuid}", "{radius-current}",
            "{radius-max}", "{modifier-current}", "{modifier-max}", "{active-abstract}", "{dig-mode}",
            "{uses}", "{blocks-mined}"),
    NOT_HOLDING_TOOL("not-holding-tool"),
    // give
    INVALID_AMOUNT("invalid-amount"),
    INVALID_TOOL("invalid-tool"),
    PLAYER_NOT_ONLINE("player-not-online"),
    GIVE_GIVER("give-giver", "{target}", "{amount}", "{tool-type}"),
    GIVE_RECEIVER("give-receiver", "{amount}", "{tool-type}"),
    // command debug
    RADIUS_NOT_ENABLED("radius-upgrade-not-enabled"),
    MODIFIER_NOT_ENABLED("modifier-upgrade-not-enabled"),
    TOOL_NOT_ENABLED("tool-mode-not-enabled"),
    SELL_NOT_ENABLED("sell-mode-not-enabled"),
    USES_NOT_ENABLED("uses-not-enabled"),
    // misc
    INVALID_COMMAND("invalid-command"),
    INCORRECT_ARGS("incorrect-args"),
    INSUFFICIENT_PERMISSION("insufficient-permission", "{node}");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    DebugMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void message(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar && receiver instanceof Player) {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replaceAll(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
