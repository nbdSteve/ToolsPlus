package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleUnInstallSubCmd extends SubCommand {

    public ModuleUnInstallSubCmd() {
        super("uninstall", 2, 3, false, PermissionNode.MODULE_UN_INSTALL);
        addAlias("u");
        addAlias("ui");
        addAlias("remove");
        addAlias("rem");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /t+ module uninstall tray
        if (args.length == 2) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        ToolsPlusModule module;
        try {
            module = ModuleManager.getInstalledModule(args[2].toUpperCase());
            if (module == null) throw new NullPointerException();
        } catch (Exception e) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        ModuleManager.uninstallModule(module);
        DebugMessage.MODULE_UN_INSTALLED.message(sender, module.getNiceName());
    }
}
