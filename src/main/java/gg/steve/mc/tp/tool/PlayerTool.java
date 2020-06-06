package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.mode.AbstractModeChange;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.UpgradeType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerTool {
    private AbstractTool tool;
    private UUID toolId;
    private int uses, blocksMined, radiusUpgradeLevel, modifierUpgradeLevel, peakRadiusUpgradeLevel, peakModifierUpgradeLevel, toolModeLevel, sellModeLevel;
    private String name;
    private AbstractGui usesGui;

    public PlayerTool(UUID toolId, NBTItem item) {
        this.toolId = toolId;
        this.uses = item.getInteger("tools+.uses");
        this.blocksMined = item.getInteger("tools+.blocks");
        this.radiusUpgradeLevel = item.getInteger("tools+.radius-upgrade-level");
        this.peakRadiusUpgradeLevel = item.getInteger("tools+.peak-radius-upgrade-level");
        this.modifierUpgradeLevel = item.getInteger("tools+.modifier-upgrade-level");
        this.peakModifierUpgradeLevel = item.getInteger("tools+.peak-modifier-upgrade-level");
        this.toolModeLevel = item.getInteger("tools+.tool-mode-level");
        this.sellModeLevel = item.getInteger("tools+.sell-mode-level");
        this.name = item.getString("tools+.name");
        this.tool = ToolsManager.getTool(this.name);
        this.usesGui = this.tool.getUsesGui();
    }

    public boolean decrementUses(Player player) {
        if (!tool.getAttributeManager().isAttributeEnabled(ToolAttributeType.USES)) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        int current = this.uses;
        int change = -1;
        this.uses += change;
        return tool.getAttributeManager().getAttribute(ToolAttributeType.USES).doUpdate(player, nbtItem, this.toolId, current, change);
    }

    public boolean incrementBlocksMined(Player player, int amount) {
        if (!tool.getAttributeManager().isAttributeEnabled(ToolAttributeType.BLOCKS_MINED)) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        int current = this.blocksMined;
        this.blocksMined += amount;
        return tool.getAttributeManager().getAttribute(ToolAttributeType.BLOCKS_MINED).doUpdate(player, nbtItem, this.toolId, current, amount);
    }

    public boolean isOnCooldown(Player player) {
        if (!tool.getAttributeManager().isAttributeEnabled(ToolAttributeType.COOLDOWN)) return false;
        return tool.getAttributeManager().getAttribute(ToolAttributeType.COOLDOWN).isOnCooldown(player, this);
    }

    public AbstractTool getAbstractTool() {
        return tool;
    }

    public String getName() {
        return name;
    }

    public int getUpgradeLevel(UpgradeType upgrade) {
        switch (upgrade) {
            case RADIUS:
                return this.radiusUpgradeLevel;
            case MODIFIER:
                return this.modifierUpgradeLevel;
            default:
                return 0;
        }
    }

    public int getCurrentMode(ModeType mode) {
        switch (mode) {
            case TOOL:
                return this.toolModeLevel;
            case SELL:
                return this.sellModeLevel;
            default:
                return 0;
        }
    }

    public void setUpgradeLevel(UpgradeType upgrade, int level) {
        switch (upgrade) {
            case RADIUS:
                this.radiusUpgradeLevel = level;
                break;
            case MODIFIER:
                this.modifierUpgradeLevel = level;
                break;
        }
    }

    public void setModeLevel(ModeType mode, int level) {
        switch (mode) {
            case TOOL:
                this.toolModeLevel = level;
                break;
            case SELL:
                this.sellModeLevel = level;
                break;
        }
    }

    public int getPeakUpgradeLevel(UpgradeType upgrade) {
        switch (upgrade) {
            case RADIUS:
                return this.peakRadiusUpgradeLevel;
            case MODIFIER:
                return this.peakModifierUpgradeLevel;
            default:
                return 0;
        }
    }

    public void setPeakUpgradeLevel(UpgradeType upgrade, int level) {
        switch (upgrade) {
            case RADIUS:
                this.peakRadiusUpgradeLevel = level;
                break;
            case MODIFIER:
                this.peakModifierUpgradeLevel = level;
                break;
        }
    }

    public UUID getToolId() {
        return toolId;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public int getBlocksMined() {
        return blocksMined;
    }

    public AbstractModeChange getModeChange(ModeType type) {
        return this.tool.getModeChange(type);
    }

    public int getRadius() {
        if (this.tool.getUpgradeManager().isUpgradeEnabled(UpgradeType.RADIUS))
            return this.tool.getUpgradeManager().getUpgrade(UpgradeType.RADIUS).getIntegerForLevel(this.radiusUpgradeLevel);
        return 0;
    }

    public double getModifier() {
        if (this.tool.getUpgradeManager().isUpgradeEnabled(UpgradeType.MODIFIER))
            return this.tool.getUpgradeManager().getUpgrade(UpgradeType.MODIFIER).getDoubleForLevel(this.modifierUpgradeLevel);
        return 0;
    }

    public boolean openUpgradeGui(Player player, UpgradeType type) {
        if (!this.tool.getUpgradeManager().isUpgradeEnabled(type)) return false;
        this.tool.getUpgradeManager().getUpgrade(type).getGui().refresh(this);
        this.tool.getUpgradeManager().getUpgrade(type).getGui().open(player);
        return true;
    }

    public boolean openModeGui(Player player, ModeType mode) {
        if (!this.tool.getModeChangeManager().isModeChangeEnabled(mode)) return false;
        this.tool.getModeChange(mode).getGui().refresh(this);
        this.tool.getModeChange(mode).getGui().open(player);
        return true;
    }

    public boolean openUsesGui(Player player) {
        if (this.usesGui == null) return false;
        this.usesGui.refresh(this);
        this.usesGui.open(player);
        return true;
    }

    public ToolData getCurrentModeData() {
        if (!this.tool.getModeChange(ModeType.TOOL).isChangingEnabled()) return this.tool.getData();
        return ToolsManager.getTool(this.tool.getModeChange(ModeType.TOOL).getCurrentModeString(this.toolModeLevel)).getData();
    }

    public double getNextUpgradePrice(UpgradeType upgrade, int level) {
        if (getPeakUpgradeLevel(upgrade) >= level && getPeakUpgradeLevel(upgrade) != 0) return 0.0;
        return tool.getUpgrade(upgrade).getUpgradePriceForLevel(level);
    }
}
