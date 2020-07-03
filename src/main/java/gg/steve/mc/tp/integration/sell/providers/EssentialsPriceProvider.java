package gg.steve.mc.tp.integration.sell.providers;

import com.earth2me.essentials.Essentials;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.integration.sell.AbstractPriceProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EssentialsPriceProvider extends AbstractPriceProvider {

    public EssentialsPriceProvider(String plugin) {
        super(plugin);
    }

    @Override
    public double getItemPrice(Player player, ItemStack item) {
        try {
            Essentials ess = Essentials.getPlugin(Essentials.class);
            return ess.getWorth().getPrice(ess, item).doubleValue();
        } catch (Exception e) {
            LogUtil.warning("Error getting item price for item " + item.getType().toString() + " from essentials economy.");
        }
        return 0;
    }

    @Override
    public void shutdown() {

    }
}
