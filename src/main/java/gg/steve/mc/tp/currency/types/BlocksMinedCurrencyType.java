package gg.steve.mc.tp.currency.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.entity.Player;

public class BlocksMinedCurrencyType extends AbstractCurrency {

    public BlocksMinedCurrencyType() {
        super(CurrencyType.BLOCKS_MINED);
    }

    @Override
    public boolean isSufficientFunds(Player player, PlayerTool tool, double cost) {
        if (tool.getBlocksMined() < cost) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(tool.getBlocksMined()),
                    ToolsPlus.formatNumber(cost),
                    getCurrencyType().getPrefix(),
                    getCurrencyType().getSuffix());
            return false;
        }
        return true;
    }
}
