package gg.steve.mc.tp.framework.cmd;

import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SubCommand {
    private String name;
    private int minArgLength, maxArgLength;
    private boolean isPlayerOnly;
    private PermissionNode node;
    private List<String> aliases;

    public SubCommand(String name, int minArgLength, int maxArgLength, boolean isPlayerOnly, PermissionNode node) {
        this.name = name;
        this.minArgLength = minArgLength;
        this.maxArgLength = maxArgLength;
        this.isPlayerOnly = isPlayerOnly;
        this.node = node;
    }

    public Player getPlayer(CommandSender sender) {
        return (Player) sender;
    }

    public UUID getPlayerId(CommandSender sender) {
        return ((Player) sender).getUniqueId();
    }

    public boolean isValidCommand(CommandSender sender, String[] args) {
        if (isPlayerOnly && !(sender instanceof Player)) {
            // do message
            return false;
        }
        if (!node.hasPermission(sender)) {
            DebugMessage.INSUFFICIENT_PERMISSION.message(sender, node.get());
            return false;
        }
        if (!isArgLengthValid(args)) {
            DebugMessage.INCORRECT_ARGS.message(sender);
            return false;
        }
        return true;
    }

    public boolean isArgLengthValid(String[] args) {
        return args.length >= minArgLength && args.length <= maxArgLength;
    }

    public void addAlias(String alias) {
        if (this.aliases == null) this.aliases = new ArrayList<>();
        aliases.add(alias);
    }

    public boolean isExecutor(String arg) {
        return this.name.equalsIgnoreCase(arg) || this.aliases.contains(arg.toLowerCase());
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
