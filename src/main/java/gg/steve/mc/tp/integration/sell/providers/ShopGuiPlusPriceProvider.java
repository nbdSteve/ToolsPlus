package gg.steve.mc.tp.integration.sell.providers;

import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.integration.sell.AbstractPriceProvider;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopGuiPlusPriceProvider extends AbstractPriceProvider {

    public ShopGuiPlusPriceProvider(String plugin) {
        super(plugin);
    }

    @Override
    public double getItemPrice(Player player, ItemStack item) {
        try {
            return ShopGuiPlusApi.getItemStackPriceSell(player, item);
        } catch (Exception e) {
            LogUtil.warning("Error getting item price for item " + item.getType().toString() + " from shopgui+ economy.");
        }
        return 0;
    }

    @Override
    public void shutdown() {

    }
}
