package gg.steve.mc.tp.upgrade;

public enum UpgradeType {
    NONE,
    RADIUS,
    MODIFIER;

    public String getLowerCaseName() {
        return name().toLowerCase();
    }
}
