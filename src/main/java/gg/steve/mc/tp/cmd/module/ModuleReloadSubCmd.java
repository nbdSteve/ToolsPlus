package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleReloadSubCmd extends SubCommand {

    public ModuleReloadSubCmd() {
        super("reload", 2, 3, false, PermissionNode.MODULE_RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            // /t+ module reload
            for (ToolsPlusModule module : ModuleManager.getInstalledModules()) {
                for (String file : module.getModuleFiles().keySet()) {
                    FileManager.getFiles().get(file).reload();
                }
            }
            ModuleManager.uninstalledAllModules();
            ModuleManager.loadInstalledModules();
            DebugMessage.RELOAD_ALL_MODULES.message(sender, ModuleManager.getModuleCount());
        } else {
            // /t+ module reload tray
            ToolsPlusModule module;
            try {
                module = ModuleManager.getInstalledModule(args[2].toUpperCase());
                if (module == null) throw new NullPointerException();
            } catch (Exception e) {
                DebugMessage.INVALID_MODULE.message(sender);
                return;
            }
            for (String file : module.getModuleFiles().keySet()) {
                FileManager.getFiles().get(file).reload();
            }
            ModuleManager.uninstallModule(module);
            ModuleManager.installToolModule(module);
            DebugMessage.RELOAD_MODULE.message(sender, module.getNiceName());
        }
    }
}
