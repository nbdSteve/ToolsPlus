package gg.steve.mc.tp.module.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.SetupManager;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.event.Listener;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public class ModuleLoaderUtil {
    private final ToolsPlus instance;

    public ModuleLoaderUtil(ToolsPlus instance) {
        this.instance = instance;
        // if no modules exist create the folder
        File moduleFolder = new File(instance.getDataFolder(), "modules");
        if (!moduleFolder.exists()) {
            moduleFolder.mkdirs();
        }
    }

    public void registerAllModules() {
        if (instance == null) {
            return;
        }
        List<Class<?>> subs = ModuleClassUtil.getClasses("modules", ToolsPlusModule.class);
        if (subs == null || subs.isEmpty()) {
            return;
        }
        for (Class<?> klass : subs) {
            ToolsPlusModule module = createInstance(klass);
            loadModule(module);
        }
    }

    public boolean registerModule(String moduleName) {
        StringBuilder builder = new StringBuilder();
        builder.append(moduleName.toUpperCase(), 0, 1);
        builder.append(moduleName.substring(1).toLowerCase());
        builder.append("Module");
        List<Class<?>> subs = ModuleClassUtil.getClasses("modules", builder.toString(), ToolsPlusModule.class);
        if (subs == null || subs.isEmpty()) {
            return false;
        }
        ToolsPlusModule module = createInstance(subs.get(0));
        loadModule(module);
        return true;
    }

    public void loadModule(ToolsPlusModule module) {
        // load files for the module
        for (String file : module.getModuleFiles().keySet()) {
            SetupManager.getFileManager().add(file, module.getModuleFiles().get(file));
        }
        for (Listener listener : module.getListeners()) {
            SetupManager.registerEvent(ToolsPlus.get(), listener);
        }
        if (module.getPlaceholderExpansion() != null) {
            SetupManager.addPlaceholderExpansion(module.getPlaceholderExpansion());
        }
        module.onLoad();
        ModuleManager.installToolModule(module);
    }

    private ToolsPlusModule createInstance(Class<?> klass) {
        if (klass == null) {
            return null;
        }
        ToolsPlusModule module = null;
        if (!ToolsPlusModule.class.isAssignableFrom(klass)) {
            return null;
        }
        try {
            Constructor<?>[] c = klass.getConstructors();
            if (c.length == 0) {
                module = (ToolsPlusModule) klass.newInstance();
            } else {
                for (Constructor<?> con : c) {
                    if (con.getParameterTypes().length == 0) {
                        module = (ToolsPlusModule) klass.newInstance();
                        break;
                    }
                }
            }
        } catch (Throwable t) {
            LogUtil.warning(t.getMessage());
            LogUtil.warning("Failed to init Tools+ module from class: " + klass.getName());
        }
        return module;
    }
}