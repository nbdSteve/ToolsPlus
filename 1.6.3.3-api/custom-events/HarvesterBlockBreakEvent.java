package dev.nuer.tp.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom event for Harvester Hoes
 */
public class HarvesterBlockBreakEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Block blockToHarvest;
    private Player player;
    private double blockPrice;
    private boolean isSelling;
    private boolean cancel;

    public HarvesterBlockBreakEvent(Block blockToHarvest, Player player, double blockPrice, boolean isSelling) {
        this.blockToHarvest = blockToHarvest;
        this.player = player;
        this.blockPrice = blockPrice;
        this.isSelling = isSelling;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Block getBlockToHarvest() {
        return blockToHarvest;
    }

    public Player getPlayer() {
        return player;
    }

    public double getBlockPrice() {
        return blockPrice;
    }

    public boolean isSelling() {
        return isSelling;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
