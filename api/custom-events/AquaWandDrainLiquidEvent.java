package dev.nuer.tp.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AquaWandDrainLiquidEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Block blockToDrain;
    private Player player;
    private boolean cancel;

    public AquaWandDrainLiquidEvent(Block blockToDrain, Player player) {
        this.blockToDrain = blockToDrain;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Block getBlockToDrain() {
        return blockToDrain;
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
}
