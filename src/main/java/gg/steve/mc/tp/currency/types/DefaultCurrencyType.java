package gg.steve.mc.tp.currency.types;

import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

public class DefaultCurrencyType extends AbstractCurrency {

    public DefaultCurrencyType() {
        super(CurrencyType.NONE);
    }

    @Override
    public boolean isSufficientFunds(Player player, PlayerTool tool, double cost) {
        return false;
    }
}
