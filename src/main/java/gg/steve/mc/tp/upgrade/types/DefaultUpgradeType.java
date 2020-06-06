package gg.steve.mc.tp.upgrade.types;

import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import org.bukkit.entity.Player;

public class DefaultUpgradeType extends AbstractUpgrade {

    public DefaultUpgradeType() {
    }

    @Override
    public boolean doUpgrade(Player player, PlayerTool tool) {
        return false;
    }

    @Override
    public boolean doDowngrade(Player player, PlayerTool tool) {
        return false;
    }
}
