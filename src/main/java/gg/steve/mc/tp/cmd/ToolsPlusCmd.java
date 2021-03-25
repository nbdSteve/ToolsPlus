package gg.steve.mc.tp.cmd;

import gg.steve.mc.tp.cmd.module.ModuleSubCmd;
import gg.steve.mc.tp.cmd.tool.ToolSubCmd;
import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.cmd.SubCommandType;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ToolsPlusCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!SubCommandType.HELP_CMD.getSub().isValidCommand(sender, args)) return true;
            SubCommandType.HELP_CMD.getSub().onCommand(sender, args);
            return true;
        }
        for (SubCommandType subCommand : SubCommandType.values()) {
            if (!subCommand.getSub().isExecutor(args[0])) continue;
            if (!subCommand.getSub().isValidCommand(sender, args)) return true;
            subCommand.getSub().onCommand(sender, args);
            return true;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tab = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommandType type : SubCommandType.values()) {
                tab.add(type.getSub().getName());
            }
            return tab;
        }
        if (args.length >= 2) {
            SubCommand sub = null;
            for (SubCommandType subCommand : SubCommandType.values()) {
                if (!subCommand.getSub().isExecutor(args[0])) continue;
                sub = subCommand.getSub();
            }
            if (sub == null) return tab;
            if (sub.getName().equalsIgnoreCase("give")) {
                if (args.length == 2) {
                    for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                    return tab;
                } else if (args.length == 3) {
                    for (String tool : ToolsManager.getTools().keySet()) tab.add(tool);
                    return tab;
                } else if (args.length == 4) {
                    tab.add("1");
                    return tab;
                }
            }
            if (sub.getName().equalsIgnoreCase("module")) {
                ModuleSubCmd module = (ModuleSubCmd) sub;
                if (args.length == 2) {
                    for (SubCommand moduleSubs : module.getModuleSubs()) {
                        tab.add(moduleSubs.getName());
                    }
                } else if (args.length == 3 && !args[1].equalsIgnoreCase("list")) {
                    for (ToolsPlusModule modules : ModuleManager.getInstalledModules())
                        tab.add(modules.getIdentifier());
                    if (args[1].equalsIgnoreCase("reload")) tab.add("ALL");
                    return tab;
                }
            } else if (sub.getName().equalsIgnoreCase("tool")) {
                ToolSubCmd tool = (ToolSubCmd) sub;
                if (args.length == 2) {
                    for (SubCommand toolSubs : tool.getToolSubs()) {
                        tab.add(toolSubs.getName());
                    }
                } else if (args.length == 3 && !args[1].equalsIgnoreCase("list")) {
                    for (Player player : Bukkit.getOnlinePlayers()) tab.add(player.getName());
                    return tab;
                }
            }
        }
        return tab;
    }
}
