package gg.steve.mc.tp.mode;

import java.util.HashMap;
import java.util.Map;

public class ToolModeChangeManager {
    private Map<ModeType, AbstractModeChange> modes;

    public ToolModeChangeManager() {
        this.modes = new HashMap<>();
    }

    public boolean addToolMode(AbstractModeChange change) {
        if (this.modes == null) return false;
        return this.modes.put(change.getType(), change) != null;
    }

    public boolean isModeChangeEnabled(ModeType type) {
        if (this.modes == null || this.modes.isEmpty()) return false;
        return this.modes.containsKey(type);
    }

    public AbstractModeChange getModeChange(ModeType type) {
        if (this.modes == null || this.modes.isEmpty()) return null;
        return this.modes.get(type);
    }
}
