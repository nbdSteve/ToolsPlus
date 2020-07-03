package gg.steve.mc.tp.integration.sell;

import gg.steve.mc.tp.integration.sell.providers.EssentialsPriceProvider;
import gg.steve.mc.tp.integration.sell.providers.InternalPriceProvider;
import gg.steve.mc.tp.integration.sell.providers.ShopGuiPlusPriceProvider;
import org.bukkit.Bukkit;

public enum PriceProviderType {
    SHOP_GUI_PLUS("shopgui+", "ShopGUIPlus"),
    ESSENTIALS("essentials", "Essentials"),
    INTERNAL("internal", "");

    private String id, plugin;
    private AbstractPriceProvider provider;

    PriceProviderType(String id, String plugin) {
        this.id = id;
        this.plugin = plugin;
        if (isEnabled()) {
            switch (this.plugin) {
                case "ShopGUIPlus":
                    this.provider = new ShopGuiPlusPriceProvider(this.plugin);
                    break;
                case "Essentials":
                    this.provider = new EssentialsPriceProvider(this.plugin);
                    break;
                default:
                    this.provider = new InternalPriceProvider(this.plugin);
                    break;
            }
        }
    }

    public String getId() {
        return id;
    }

    public static PriceProviderType getProvider(String id) {
        for (PriceProviderType provider : PriceProviderType.values()) {
            if (provider.getId().equalsIgnoreCase(id)) return provider;
        }
        return INTERNAL;
    }

    public boolean isEnabled() {
        if (this.plugin.equalsIgnoreCase("")) return true;
        return Bukkit.getPluginManager().getPlugin(this.plugin) != null;
    }

    public AbstractPriceProvider getPriceProvider() {
        return provider;
    }
}
