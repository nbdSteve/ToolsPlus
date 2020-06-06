package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd() {
        super("help", 0, 1, false, PermissionNode.HELP);
        addAlias("h");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GeneralMessage.HELP.message(sender,
                ToolsPlus.getVersion(),
                ModuleManager.getModuleCount(),
                ModuleManager.getModulesAsList(),
                ToolsManager.getAbstractToolCount(),
                ToolsManager.getAbstractToolsAsList(),
                ToolsManager.getPlayerToolCount());
    }
}
