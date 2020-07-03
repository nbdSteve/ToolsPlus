package gg.steve.mc.tp.framework.cmd.misc;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(ToolsPlus.get());
        ToolsPlus.get().onLoad();
        Bukkit.getPluginManager().enablePlugin(ToolsPlus.get());
        GeneralMessage.RELOAD.message(sender, ModuleManager.getModuleCount());
    }
}
