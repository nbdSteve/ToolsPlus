package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleListSubCmd extends SubCommand {

    public ModuleListSubCmd() {
        super("list", 2, 3, false, PermissionNode.MODULE_LIST);
        addAlias("l");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /t+ module list
        DebugMessage.LIST_MODULES.message(sender, ModuleManager.getModuleCount(), ModuleManager.getModulesAsList());
    }
}
