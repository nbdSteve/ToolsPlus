package gg.steve.mc.tp.currency.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

public class XpLevelCurrencyType extends AbstractCurrency {

    public XpLevelCurrencyType() {
        super(CurrencyType.XP_LEVEL);
    }

    @Override
    public boolean isSufficientFunds(Player player, PlayerTool tool, double cost) {
        if (player.getLevel() < cost) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(player.getLevel()),
                    ToolsPlus.formatNumber(cost),
                    getCurrencyType().getPrefix(),
                    getCurrencyType().getSuffix());
            return false;
        }
        player.setLevel((int) (player.getLevel() - cost));
        return true;
    }
}
