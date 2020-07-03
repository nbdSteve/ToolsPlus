package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class ModuleInfoSubCmd extends SubCommand {

    public ModuleInfoSubCmd() {
        super("info", 2, 3, false, PermissionNode.MODULE_INFO);
        addAlias("i");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /t+ module info tray
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
        DebugMessage.MODULE_INFO.message(sender,
                module.getModuleName(),
                module.getAuthor(),
                module.getVersion(),
                module.getIdentifier(),
                ToolsManager.getToolAmount(module));
    }
}
