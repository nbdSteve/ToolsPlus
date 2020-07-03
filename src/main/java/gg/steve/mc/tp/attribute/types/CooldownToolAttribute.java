package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.attribute.utils.CooldownUtil;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CooldownToolAttribute extends AbstractToolAttribute {
    private static HashMap<UUID, List<CooldownUtil>> playersOnCooldown;

    public static void initialise() {
        playersOnCooldown = new HashMap<>();
    }

    public static void shutdown() {
        if (playersOnCooldown != null && !playersOnCooldown.isEmpty()) playersOnCooldown.clear();
    }

    public static boolean isCooldownActive(Player player, PlayerTool tool) {
        UUID playerId = player.getUniqueId();
        if (!playersOnCooldown.containsKey(playerId)) {
            return false;
        }
        for (CooldownUtil cooldown : playersOnCooldown.get(playerId)) {
            if (cooldown.getTool().equalsIgnoreCase(tool.getName()) || cooldown.getTool().equalsIgnoreCase(tool.getAbstractTool().getModuleId())) {
                if (cooldown.isActive()) {
                    GeneralMessage.COOLDOWN.message(player, tool.getAbstractTool().getModule().getNiceName(), ToolsPlus.formatNumber(cooldown.getRemaining()));
                    return true;
                }
                // if the players cooldown has ended, remove them from the list
                return false;
            }
        }
        return false;
    }

    public CooldownToolAttribute(int duration) {
        super(ToolAttributeType.COOLDOWN, duration);
    }

    @Override
    public boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost) {
        return true;
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        return true;
    }

    @Override
    public boolean isOnCooldown(Player player, PlayerTool tool) {
        UUID playerId = player.getUniqueId();
        if (!playersOnCooldown.containsKey(playerId)) {
            // add a new cooldown since the only way to query is by using the tool
            playersOnCooldown.put(playerId, new ArrayList<>());
            if (Files.CONFIG.get().getBoolean("per-tool-cooldowns")) {
                playersOnCooldown.get(playerId).add(new CooldownUtil(tool.getName(), getDuration()));
            } else {
                playersOnCooldown.get(playerId).add(new CooldownUtil(tool.getAbstractTool().getModuleId(), getDuration()));
            }
            return false;
        }
        for (CooldownUtil cooldown : playersOnCooldown.get(playerId)) {
            if (cooldown.getTool().equalsIgnoreCase(tool.getName()) || cooldown.getTool().equalsIgnoreCase(tool.getAbstractTool().getModuleId())) {
                if (cooldown.isActive()) {
                    GeneralMessage.COOLDOWN.message(player, tool.getAbstractTool().getModule().getNiceName(), ToolsPlus.formatNumber(cooldown.getRemaining()));
                    return true;
                }
                // if the players cooldown has ended, remove them from the list
                playersOnCooldown.get(playerId).remove(cooldown);
                // add a new cooldown since the only way to query is by using the tool
                playersOnCooldown.get(playerId).add(new CooldownUtil(tool.getName(), getDuration()));
                return false;
            }
        }
        if (Files.CONFIG.get().getBoolean("per-tool-cooldowns")) {
            playersOnCooldown.get(playerId).add(new CooldownUtil(tool.getName(), getDuration()));
        } else {
            playersOnCooldown.get(playerId).add(new CooldownUtil(tool.getAbstractTool().getModuleId(), getDuration()));
        }
        return false;
    }
}
