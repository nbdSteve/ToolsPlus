package gg.steve.mc.tp.currency;

import gg.steve.mc.tp.currency.types.*;
import gg.steve.mc.tp.framework.yml.Files;

public enum CurrencyType {
    VAULT("vault"),
    XP_RAW("xp-raw"),
    XP_LEVEL("xp-level"),
    BLOCKS_MINED("blocks-mined"),
    NONE("");

    private String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getNiceName() {
        StringBuilder builder = new StringBuilder();
        builder.append(name(), 0, 1);
        builder.append(name().substring(1).toLowerCase());
        return builder.toString();
    }

    public String getPrefix() {
        if (name().equalsIgnoreCase("none")) return type;
        return Files.CONFIG.get().getString("currency." + type + ".prefix");
    }

    public String getSuffix() {
        if (name().equalsIgnoreCase("none")) return type;
        return Files.CONFIG.get().getString("currency." + type + ".suffix");
    }

    public static AbstractCurrency getCurrencyFromString(String currency) {
        switch (currency) {
            case "vault":
                return new VaultCurrencyType();
            case "blocks-mined":
                return new BlocksMinedCurrencyType();
            case "xp-raw":
                return new XpRawCurrencyType();
            case "xp-level":
                return new XpLevelCurrencyType();
        }
        return AbstractCurrency.defaultCurrency;
    }
}
