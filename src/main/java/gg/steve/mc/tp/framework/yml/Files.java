package gg.steve.mc.tp.framework.yml;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Files {
    // generic
    CONFIG("tools+.yml"),
    // omni
    OMNI_CONFIG("omni.yml"),
    // permissions
    PERMISSIONS("permissions.yml"),
    // lang
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml");
    // trench
//    TRENCH_CONFIG("configs" + File.separator + "trench.yml"),
//    // sell
//    SELL_CONFIG("configs" + File.separator + "sell.yml"),
//    // tray
//    TRAY_CONFIG("configs" + File.separator + "tray.yml");

    private final String path;

    Files(String path) {
        this.path = path;
    }

    public void load(FileManagerUtil fileManagerUtil) {
        fileManagerUtil.add(name(), this.path);
    }

    public YamlConfiguration get() {
        return FileManagerUtil.get(name());
    }

    public void save() {
        FileManagerUtil.save(name());
    }

    public static void reload() {
        FileManagerUtil.reload();
    }
}
