package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ModuleSubCmd extends SubCommand {
    private List<SubCommand> moduleSubs = new ArrayList<>();

    public ModuleSubCmd() {
        super("module", 1, 3, false, PermissionNode.MODULE_LIST);
        addAlias("m");
        addAlias("mods");
        addAlias("modules");
        this.moduleSubs.add(new ModuleReloadSubCmd());
        this.moduleSubs.add(new ModuleListSubCmd());
        this.moduleSubs.add(new ModuleInfoSubCmd());
        this.moduleSubs.add(new ModuleInstallSubCmd());
        this.moduleSubs.add(new ModuleUnInstallSubCmd());
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            DebugMessage.LIST_MODULES.message(sender, ModuleManager.getModuleCount(), ModuleManager.getModulesAsList());
            return;
        }
        for (SubCommand sub : moduleSubs) {
            if (!sub.isExecutor(args[1])) continue;
            if (!sub.isValidCommand(sender, args)) return;
            sub.onCommand(sender, args);
            return;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
    }

    public List<SubCommand> getModuleSubs() {
        return moduleSubs;
    }
}
