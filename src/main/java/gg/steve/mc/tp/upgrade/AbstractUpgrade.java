package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.currency.types.DefaultCurrencyType;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.gui.GuiManager;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.upgrade.types.DefaultUpgradeType;
import gg.steve.mc.tp.utils.ColorUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractUpgrade {
    public static DefaultUpgradeType defaultUpgrade = new DefaultUpgradeType();

    private UpgradeType type;
    private AbstractCurrency currency;
    private Map<Integer, List<Object>> track;
    private String updateString;
    private AbstractGui gui;
    private boolean isDowngrade;

    public AbstractUpgrade() {
        this.type = UpgradeType.NONE;
        this.currency = AbstractCurrency.defaultCurrency;
        this.isDowngrade = false;
    }

    public AbstractUpgrade(UpgradeType type, PluginFile file) {
        this.type = type;
        this.currency = CurrencyType.getCurrencyFromString(file.get().getString("upgrades." + type.getLowerCaseName() + ".currency"));
        this.gui = GuiManager.getGui(file.get().getString("upgrades." + type.getLowerCaseName() + ".gui"));
        this.track = new HashMap<>();
        int pos = 0;
        for (String entry : file.get().getStringList("upgrades." + type.getLowerCaseName() + ".track")) {
            track.put(pos, new ArrayList<>());
            String[] parts = entry.split(":");
            track.get(pos).add(parts[0]);
            track.get(pos).add(parts[1]);
            track.get(pos).add(parts[2]);
            track.get(pos).add(parts[3]);
            pos++;
        }
        this.updateString = ColorUtil.colorize(file.get().getString("upgrades." + type.getLowerCaseName() + ".lore-update-string"));
        this.isDowngrade = file.get().getBoolean("upgrades." + type.getLowerCaseName() + ".allow-downgrade");
    }

    public boolean isUpgradeable() {
        return this.track.size() > 1;
    }

    public double getDoubleForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0.0;
        return Double.parseDouble((String) this.track.get(level).get(0));
    }

    public int getIntegerForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0;
        return Integer.parseInt((String) this.track.get(level).get(0));
    }

    public double getUpgradePriceForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0.0;
        try {
            return Double.parseDouble((String) this.track.get(level + 1).get(1));
        } catch (Exception e) {
            return Double.parseDouble((String) this.track.get(level).get(1));
        }
    }

    public double getDowngradePriceForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0.0;
        try {
            return Double.parseDouble((String) this.track.get(level - 1).get(2));
        } catch (Exception e) {
            return Double.parseDouble((String) this.track.get(level).get(2));
        }
    }

    public String getLoreStringForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return "Not upgradeable";
        try {
            return (String) this.track.get(level).get(3);
        } catch (Exception e) {
            return ColorUtil.colorize(Files.CONFIG.get().getString("max-upgrade-placeholder"));
        }
    }

    public String getUpdateString() {
        if (this.type == UpgradeType.NONE) return "";
        return updateString;
    }

    public int getMaxLevel() {
        if (this.type == UpgradeType.NONE) return 0;
        return track.size() - 1;
    }

    public int getMinLevel() {
        return 0;
    }

    public boolean isDegradable() {
        return isDowngrade;
    }

    public UpgradeType getType() {
        return type;
    }

    public AbstractCurrency getCurrency() {
        if (currency == null) return new DefaultCurrencyType();
        return currency;
    }

    public AbstractGui getGui() {
        return gui;
    }

    public abstract boolean doUpgrade(Player player, PlayerTool tool);

    public abstract boolean doDowngrade(Player player, PlayerTool tool);
}
