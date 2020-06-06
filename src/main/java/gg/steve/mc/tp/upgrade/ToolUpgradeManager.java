package gg.steve.mc.tp.upgrade;

import java.util.HashMap;
import java.util.Map;

public class ToolUpgradeManager {
    private Map<UpgradeType, AbstractUpgrade> upgrades;

    public ToolUpgradeManager() {
        this.upgrades = new HashMap<>();
    }

    public boolean addToolUpgrade(AbstractUpgrade upgrade) {
        if (this.upgrades == null) return false;
        return this.upgrades.put(upgrade.getType(), upgrade) != null;
    }

    public boolean isUpgradeEnabled(UpgradeType type) {
        if (this.upgrades == null || this.upgrades.isEmpty()) return false;
        return this.upgrades.containsKey(type);
    }

    public AbstractUpgrade getUpgrade(UpgradeType type) {
        if (this.upgrades == null || this.upgrades.isEmpty()) return null;
        return this.upgrades.get(type);
    }
}