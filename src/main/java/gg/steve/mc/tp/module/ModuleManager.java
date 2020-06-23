package gg.steve.mc.tp.module;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.module.utils.ModuleLoaderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.apache.commons.lang.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private static Map<String, ToolsPlusModule> modules;
    private static ModuleLoaderUtil loader;

    public static void initialise(ToolsPlus instance) {
        modules = new HashMap<>();
        loader = new ModuleLoaderUtil(instance);
    }

    public static void loadInstalledModules() {
        loader.registerAllModules();
        StringBuilder message = new StringBuilder();
        if (modules.size() > 0) {
            message.append(": (");
            int i = 0;
            for (String identifier : modules.keySet()) {
                message.append(modules.get(identifier).getModuleName());
                if (i != modules.size() - 1) {
                    message.append(", ");
                } else {
                    message.append(")");
                }
                i++;
            }
            LogUtil.info("Successfully loaded " + modules.size() + " tool module(s)" + message + ".");
        } else {
            LogUtil.warning("<>");
            LogUtil.warning("Uh oh! You don't have any tool modules installed, the plugin is essentially useless.");
            LogUtil.warning("Please see the spigot page for reference on how to install modules, or contact nbdSteve#0583 on discord.");
            LogUtil.warning("<>");
        }
    }

    public static boolean installModule(String moduleName) {
        if (modules.containsKey(moduleName.toUpperCase())) {
            return false;
        }
        return loader.registerModule(moduleName);
    }

    public static boolean installToolModule(ToolsPlusModule module) {
        Validate.notNull(module.getIdentifier(), "Module identifier can not be null");
        Validate.notNull(module, "ToolsPlusModule can not be null");
        if (modules.containsKey(module.getIdentifier())) {
            return false;
        }
        modules.put(module.getIdentifier(), module);
        return true;
    }

    public static boolean isInstalled(String identifier) {
        if (modules == null || modules.isEmpty()) return false;
        return modules.get(identifier) != null;
    }

    public static ToolsPlusModule getInstalledModule(String identifier) {
        if (!isInstalled(identifier)) return null;
        return modules.get(identifier);
    }

    public static boolean uninstallModule(ToolsPlusModule module) {
        if (modules == null || modules.isEmpty()) return false;
        module.onShutdown();
        return modules.remove(module.getIdentifier()) != null;
    }

    public static void uninstalledAllModules() {
        if (modules == null || modules.isEmpty()) return;
        for (ToolsPlusModule module : modules.values()) {
            module.onShutdown();
        }
        modules.clear();
    }

    public static String getModulesAsList() {
        StringBuilder message = new StringBuilder();
        if (modules.size() > 0) {
            int i = 0;
            for (String identifier : modules.keySet()) {
                message.append(modules.get(identifier).getModuleName());
                if (i != modules.size() - 1) {
                    message.append(", ");
                }
                i++;
            }
        }
        return message.toString();
    }

    public static String getModuleCount() {
        if (modules == null) return "0";
        return String.valueOf(modules.size());
    }

    public static Collection<ToolsPlusModule> getInstalledModules() {
        return modules.values();
    }
}
