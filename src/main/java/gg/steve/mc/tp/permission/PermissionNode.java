package gg.steve.mc.tp.permission;

import gg.steve.mc.tp.managers.Files;
import org.bukkit.command.CommandSender;

public enum PermissionNode {
    // cmd
    RELOAD("command.reload"),
    GIVE("command.give"),
    HELP("command.help"),
    // module,
    MODULE_LIST("module.list"),
    MODULE_RELOAD("module.reload"),
    MODULE_INFO("module.info"),
    MODULE_UN_INSTALL("module.un-install"),
    MODULE_INSTALL("module.install"),
    // tool
    TOOL_LIST("tool.list"),
    TOOL_INFO("tool.info"),
    // gui
    USE("gui.use"),
    TRENCH("gui.trench");

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return Files.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }

    public static boolean isPurchasePerms() {
        return Files.PERMISSIONS.get().getBoolean("purchase.enabled");
    }
}
