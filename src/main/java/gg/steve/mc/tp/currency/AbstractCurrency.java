package gg.steve.mc.tp.currency;

import gg.steve.mc.tp.currency.types.DefaultCurrencyType;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

public abstract class AbstractCurrency {
    public static DefaultCurrencyType defaultCurrency = new DefaultCurrencyType();

    private CurrencyType currency;

    public AbstractCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public CurrencyType getCurrencyType() {
        return currency;
    }

    public String getPrefix() {
        return currency.getPrefix();
    }

    public String getSuffix() {
        return currency.getSuffix();
    }

    public String getNiceName() {
        return currency.getNiceName();
    }

    public abstract boolean isSufficientFunds(Player player, PlayerTool tool, double cost);
}
