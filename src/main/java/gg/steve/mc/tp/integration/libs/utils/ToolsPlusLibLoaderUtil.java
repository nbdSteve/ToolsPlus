package gg.steve.mc.tp.integration.libs.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.integration.libs.ToolsPlusLib;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibManager;
import gg.steve.mc.tp.integration.libs.ToolsPlusLibType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.utils.FileClassUtil;
import gg.steve.mc.tp.framework.utils.LogUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public class ToolsPlusLibLoaderUtil {
    private final ToolsPlus instance;

    public ToolsPlusLibLoaderUtil(ToolsPlus instance) {
        this.instance = instance;
        // if no modules exist create the folder
        File moduleFolder = new File(instance.getDataFolder(), "libs");
        if (!moduleFolder.exists()) {
            moduleFolder.mkdirs();
        }
    }

    public void registerAllLibs() {
        if (instance == null) {
            return;
        }
        List<Class<?>> subs = FileClassUtil.getClasses("libs", ToolsPlusLib.class);
        if (subs == null || subs.isEmpty()) {
            return;
        }
        for (Class<?> klass : subs) {
            ToolsPlusLib lib = createInstance(klass);
            loadLib(lib);
        }
    }

    public boolean registerLib(ToolsPlusLibType libType) {
        List<Class<?>> subs = FileClassUtil.getClasses("modules", libType.getLibName(), ToolsPlusModule.class);
        if (subs == null || subs.isEmpty()) {
            return false;
        }
        ToolsPlusLib lib = createInstance(subs.get(0));
        loadLib(lib);
        return true;
    }

    public void loadLib(ToolsPlusLib lib) {
        // load files for the module
        ToolsPlusLibManager.installLib(lib);
    }

    private ToolsPlusLib createInstance(Class<?> klass) {
        if (klass == null) {
            return null;
        }
        ToolsPlusLib lib = null;
        if (!ToolsPlusLib.class.isAssignableFrom(klass)) {
            return null;
        }
        try {
            Constructor<?>[] c = klass.getConstructors();
            if (c.length == 0) {
                lib = (ToolsPlusLib) klass.newInstance();
            } else {
                for (Constructor<?> con : c) {
                    if (con.getParameterTypes().length == 0) {
                        lib = (ToolsPlusLib) klass.newInstance();
                        break;
                    }
                }
            }
        } catch (Throwable t) {
            LogUtil.warning(t.getMessage());
            LogUtil.warning("Failed to init Tools+ module from class: " + klass.getName());
        }
        return lib;
    }
}
