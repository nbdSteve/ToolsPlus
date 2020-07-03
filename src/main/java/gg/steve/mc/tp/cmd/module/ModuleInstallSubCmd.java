package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleInstallSubCmd extends SubCommand {

    public ModuleInstallSubCmd() {
        super("install", 2, 3, false, PermissionNode.MODULE_INSTALL);
        addAlias("add");
        addAlias("ins");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // t+ module install tray
        if (args.length == 2) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        ToolsPlusModule module;
        if (ModuleManager.getInstalledModule(args[2].toUpperCase()) != null) {
            DebugMessage.MODULE_ALREADY_INSTALLED.message(sender);
            return;
        }
        if (!ModuleManager.installModule(args[2])) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        module = ModuleManager.getInstalledModule(args[2].toUpperCase());
        DebugMessage.MODULE_INSTALLED.message(sender, module.getModuleName());
    }
}
