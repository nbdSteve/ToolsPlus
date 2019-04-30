package dev.nuer.tp.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom event for the Lightning Wands
 */
public class LightningWandStrikeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Block blockToStrike;
    private Player player;
    private Creeper creeperToPower;
    private boolean cancel;

    public LightningWandStrikeEvent(Block blockToStrike, Player player) {
        this.blockToStrike = blockToStrike;
        this.player = player;
    }

    public LightningWandStrikeEvent(Block blockToStrike, Player player, Creeper creeperToPower) {
        this.blockToStrike = blockToStrike;
        this.player = player;
        this.creeperToPower = creeperToPower;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Block getBlockToStrike() {
        return blockToStrike;
    }

    public Player getPlayer() {
        return player;
    }

    public Creeper getCreeperToPower() {
        return creeperToPower;
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
