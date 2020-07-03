package gg.steve.mc.tp.integration.libs;

public enum ToolsPlusLibType {
    WORLDGUARD_v7("WorldGuard-v7-Lib"),
    WORLDGUARD_LEGACY("WorldGuard-Legacy-Lib"),
    MASSIVE_FACTIONS("MassiveFactions-Lib"),
    FACTIONS_UUID("FactionsUUID-Lib"),
    FACTIONS_X("FactionsX-Lib"),
    SABER_FACTIONS("SaberFactions-Lib"),
    SAVAGE_FACTIONS("SavageFactions-Lib");

    private String libName;

    ToolsPlusLibType(String libName) {
        this.libName = libName;
    }

    public String getLibName() {
        return libName;
    }
}
