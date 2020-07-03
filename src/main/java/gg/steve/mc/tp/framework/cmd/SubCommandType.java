package gg.steve.mc.tp.framework.cmd;

import gg.steve.mc.tp.cmd.give.GiveSubCmd;
import gg.steve.mc.tp.cmd.module.ModuleSubCmd;
import gg.steve.mc.tp.framework.cmd.misc.HelpSubCmd;
import gg.steve.mc.tp.framework.cmd.misc.ReloadSubCmd;
import gg.steve.mc.tp.cmd.tool.ToolSubCmd;

public enum SubCommandType {
    HELP_CMD(new HelpSubCmd()),
    TOOL_LIST_CMD(new ToolSubCmd()),
    MODULE_LIST_CMD(new ModuleSubCmd()),
    GIVE_CMD(new GiveSubCmd()),
    RELOAD_CMD(new ReloadSubCmd());

    private SubCommand sub;

    SubCommandType(SubCommand sub) {
        this.sub = sub;
    }

    public SubCommand getSub() {
        return sub;
    }
}
