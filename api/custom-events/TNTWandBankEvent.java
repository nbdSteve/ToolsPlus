package dev.nuer.tp.events;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom bank event for TNT Wands
 */
public class TNTWandBankEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Chest chestBeingAffected;
    private Player player;
    private boolean cancel;

    public TNTWandBankEvent(Chest chestBeingAffected, Player player) {
        this.chestBeingAffected = chestBeingAffected;
        this.player = player;
    }

    public Chest getChestBeingAffected() {
        return chestBeingAffected;
    }

    public Player getPlayer() {
        return player;
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

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
