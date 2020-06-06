package dev.nuer.tp.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom event for the Tray Tools
 */
public class TrayBlockBreakEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Block blockToBeBroken;
    private Player player;
    private boolean cancel;

    public TrayBlockBreakEvent(Block blockToBeBroken, Player player) {
        this.blockToBeBroken = blockToBeBroken;
        this.player = player;
    }

    public Block getBlockToBeBroken() {
        return blockToBeBroken;
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
