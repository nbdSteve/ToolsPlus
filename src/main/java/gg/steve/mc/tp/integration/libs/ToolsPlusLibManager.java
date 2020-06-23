package gg.steve.mc.tp.integration.libs;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.integration.libs.utils.ToolsPlusLibLoaderUtil;
import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;

public class ToolsPlusLibManager {
    private static Map<ToolsPlusLibType, ToolsPlusLib> libs;
    private static ToolsPlusLibLoaderUtil loader;

    public static void initialise(ToolsPlus instance) {
        libs = new HashMap<>();
        loader = new ToolsPlusLibLoaderUtil(instance);
    }

    public static void loadInstalledLibs() {
        loader.registerAllLibs();
    }

    public static void uninstalledAllLibs() {
        if (libs == null ||libs.isEmpty()) return;
        libs.clear();
    }

    public static boolean installLib(ToolsPlusLib lib) {
        Validate.notNull(lib, "ToolsPlusLib can not be null");
        if (libs.containsKey(lib.getLibType())) {
            return false;
        }
        libs.put(lib.getLibType(), lib);
        return true;
    }

    public static ToolsPlusLib getLibByType(ToolsPlusLibType libType) {
        return libs.get(libType);
    }
}
