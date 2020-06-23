package gg.steve.mc.tp.mode;

import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.gui.GuiManager;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.utils.ColorUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractModeChange {
    private ModeType type;
    private AbstractCurrency currency;
    private HashMap<Integer, List<Object>> track;
    private boolean sneakSwitch, rightClickSwitch;
    private String updateString;
    private AbstractGui gui;

    public AbstractModeChange(ModeType type, PluginFile file) {
        this.type = type;
        this.track = new HashMap<>();
        int pos = 0;
        for (String entry : file.get().getStringList("modes." + type.name().toLowerCase() + ".track")) {
            track.put(pos, new ArrayList<>());
            String[] parts = entry.split(":");
            track.get(pos).add(parts[0]);
            track.get(pos).add(parts[1]);
            track.get(pos).add(parts[2]);
            pos++;
        }
        this.sneakSwitch = file.get().getBoolean("modes." + type.name().toLowerCase() + ".sneak-switch");
        this.rightClickSwitch = file.get().getBoolean("modes." + type.name().toLowerCase() + ".right-click-switch");
        this.currency = CurrencyType.getCurrencyFromString(file.get().getString("modes." + type.name().toLowerCase() + ".currency"));
        this.updateString = ColorUtil.colorize(file.get().getString("modes." + type.name().toLowerCase() + ".lore-update-string"));
        this.gui = GuiManager.getGui(file.get().getString("modes." + type.name().toLowerCase() + ".gui"));
    }

    public boolean isChangingEnabled() {
        return track.size() > 1;
    }

    public double getChangePriceForMode(int current) {
        return Double.parseDouble((String) track.get(getNextMode(current)).get(1));
    }

    public int getNextMode(int current) {
        if (current + 1 < track.size()) return current + 1;
        return 0;
    }

    public String getCurrentModeString(int current) {
        return (String) track.get(current).get(0);
    }

    public String getCurrentModeLore(int current) {
        try {
            return (String) track.get(current).get(2);
        } catch (NullPointerException e) {
            return "debug";
        }
    }

    public String getNextModeLore(int current) {
        if (!isChangingEnabled()) return Files.CONFIG.get().getString("no-mode-change-placeholder");
        return (String) track.get(getNextMode(current)).get(2);
    }

    public boolean isSneakSwitch() {
        return sneakSwitch;
    }

    public boolean isRightClickSwitch() {
        return rightClickSwitch;
    }

    public ModeType getType() {
        return type;
    }

    public AbstractCurrency getCurrency() {
        return currency;
    }

    public Map<Integer, List<Object>> getTrack() {
        return track;
    }

    public String getUpdateString() {
        return updateString;
    }

    public AbstractGui getGui() {
        return gui;
    }

    public abstract boolean changeMode(Player player, PlayerTool tool);
}
