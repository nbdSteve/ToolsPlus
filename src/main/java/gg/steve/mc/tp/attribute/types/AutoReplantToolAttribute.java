package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AutoReplantToolAttribute extends AbstractToolAttribute {

    public AutoReplantToolAttribute(String updateString) {
        super(ToolAttributeType.AUTO_REPLANT, updateString);
    }

    @Override
    public boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost) {
        return false;
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        return false;
    }

    @Override
    public boolean isOnCooldown(Player player, PlayerTool tool) {
        return false;
    }
}
