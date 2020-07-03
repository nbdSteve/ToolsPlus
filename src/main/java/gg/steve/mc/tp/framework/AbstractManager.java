package gg.steve.mc.tp.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractManager {
    private static List<AbstractManager> managers = new ArrayList<>();

    public AbstractManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialiseManagers() {
        for (AbstractManager manager : managers) {
            manager.onLoad();
        }
    }

    public static void shutdownManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers) {
            manager.onShutdown();
        }
        managers.clear();
    }

    public abstract void onLoad();

    public abstract void onShutdown();
}
