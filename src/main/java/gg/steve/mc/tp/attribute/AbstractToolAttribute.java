package gg.steve.mc.tp.attribute;

import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.framework.utils.ColorUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class AbstractToolAttribute {
    private ToolAttributeType type;
    private String updateString;
    private int duration;

    public AbstractToolAttribute(ToolAttributeType type, String updateString) {
        this.type = type;
        this.updateString = ColorUtil.colorize(updateString);
    }

    public AbstractToolAttribute(ToolAttributeType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public ToolAttributeType getType() {
        return type;
    }

    public String getUpdateString() {
        return updateString;
    }

    public int getDuration() {
        return duration;
    }

    public abstract boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost);

    public abstract boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change);

    public abstract boolean isOnCooldown(Player player, PlayerTool tool);
}
