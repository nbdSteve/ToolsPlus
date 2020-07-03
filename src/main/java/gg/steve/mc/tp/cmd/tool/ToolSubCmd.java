package gg.steve.mc.tp.cmd.tool;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ToolSubCmd extends SubCommand {
    private List<SubCommand> toolSubs = new ArrayList<>();

    public ToolSubCmd() {
        super("tool", 1, 3, false, PermissionNode.TOOL_LIST);
        addAlias("t");
        addAlias("tools");
        toolSubs.add(new ToolInfoSubCmd());
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            DebugMessage.LIST_TOOLS.message(sender, ToolsManager.getAbstractToolCount(), ToolsManager.getAbstractToolsAsList(), ToolsManager.getPlayerToolCount());
            return;
        }
        for (SubCommand sub : toolSubs) {
            if (!sub.isExecutor(args[1])) continue;
            if (!sub.isValidCommand(sender, args)) return;
            sub.onCommand(sender, args);
            return;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
    }
}