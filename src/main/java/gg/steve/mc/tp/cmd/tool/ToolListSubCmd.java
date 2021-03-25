package gg.steve.mc.tp.cmd.tool;

import gg.steve.mc.tp.framework.cmd.SubCommand;
import gg.steve.mc.tp.framework.message.DebugMessage;
import gg.steve.mc.tp.framework.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class ToolListSubCmd extends SubCommand {

    public ToolListSubCmd() {
        super("list", 2, 2, false, PermissionNode.TOOL_LIST);
        addAlias("l");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        DebugMessage.LIST_TOOLS.message(sender, ToolsManager.getAbstractToolCount(), ToolsManager.getAbstractToolsAsList(), ToolsManager.getPlayerToolCount());
    }
}
