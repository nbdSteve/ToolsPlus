package gg.steve.mc.tp.cmd.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.upgrade.UpgradeType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToolInfoSubCmd extends SubCommand {

    public ToolInfoSubCmd() {
        super("info", 3, 3, false, PermissionNode.TOOL_INFO);
        addAlias("i");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /t+ tool info trench3x3
        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            DebugMessage.PLAYER_NOT_ONLINE.message(sender);
            return;
        }
        if (!PlayerToolManager.isHoldingTool(target.getUniqueId())) {
            DebugMessage.NOT_HOLDING_TOOL.message(sender);
            return;
        }
        PlayerTool tool;
        try {
            tool = PlayerToolManager.getToolPlayer(target.getUniqueId()).getPlayerTool();
        } catch (Exception e) {
            DebugMessage.NOT_HOLDING_TOOL.message(sender);
            return;
        }
        DebugMessage.PLAYER_TOOL_INFO.message(sender, target.getName(),
                String.valueOf(tool.getToolId()),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel() + 1),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel() + 1),
                tool.getModeChange(ModeType.TOOL).getCurrentModeLore(tool.getCurrentMode(ModeType.TOOL)),
                tool.getModeChange(ModeType.SELL).getCurrentModeLore(tool.getCurrentMode(ModeType.SELL)),
                ToolsPlus.formatNumber(tool.getUses()),
                ToolsPlus.formatNumber(tool.getBlocksMined()));
    }
}
