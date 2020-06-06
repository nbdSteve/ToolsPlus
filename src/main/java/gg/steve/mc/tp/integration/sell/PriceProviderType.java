package gg.steve.mc.tp.integration.sell;

public enum PriceProviderType {
    SHOP_GUI_PLUS("shopgui+"),
    ESSENTIALS("essentials"),
    INTERNAL("internal");

    private String id;

    PriceProviderType(String id) {
        this.id = id;
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
}
