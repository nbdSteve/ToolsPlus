package gg.steve.mc.tp.framework.message;

import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.tp.framework.yml.Files;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum GeneralMessage {
    RELOAD("reload", "{modules-number}"),
    HELP("help", "{version}", "{modules-number}", "{modules-list}", "{tools-number}", "{tools-list}", "{player-tools-number}"),
    OUT_OF_USES("out-of-uses", "{tool-type}"),
    UPGRADE("upgrade", "{tool-type}", "{level}", "{max}", "{cost}", "{currency-prefix}", "{currency-suffix}"),
    DOWNGRADE("downgrade", "{tool-type}", "{level}", "{max}", "{cost}", "{currency-prefix}", "{currency-suffix}"),
    INVENTORY_FULL("inventory-full"),
    TOOL_MAX_LEVEL("tool-max-level"),
    TOOL_MIN_LEVEL("tool-min-level"),
    USES_PURCHASE("uses-purchase", "{amount}", "{cost}", "{currency-prefix}", "{currency-suffix}"),
    SALE("sale", "{tool-type}", "{amount}", "{deposit}"),
    NO_SALE("no-sale", "{tool-type}"),
    COOLDOWN("cooldown-query", "{tool-type}", "{seconds}"),
    MODE_CHANGE("mode-change", "{change}", "{cost}", "{currency-prefix}", "{currency-suffix}"),
    INSUFFICIENT_FUNDS("insufficient-funds", "{balance}", "{cost}", "{currency-prefix}", "{currency-suffix}");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    GeneralMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line))) {
                    receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                }
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
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
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line))) {
                    ((Player) receiver).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                }
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public static void doMessage(Player receiver, List<String> lines) {
        for (String line : lines) {
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }
}