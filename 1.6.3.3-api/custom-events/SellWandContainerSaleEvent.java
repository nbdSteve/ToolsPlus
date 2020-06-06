package dev.nuer.tp.events;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Custom event for the Sell Wands
 */
public class SellWandContainerSaleEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Chest chestToSell;
    private Player player;
    private ItemStack itemBeingSold;
    private double itemPrice;
    private boolean cancel;

    public SellWandContainerSaleEvent(Chest chestToSell, Player player, ItemStack itemBeingSold, double itemPrice) {
        this.chestToSell = chestToSell;
        this.player = player;
        this.itemBeingSold = itemBeingSold;
        this.itemPrice = itemPrice;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Chest getChestToSell() {
        return chestToSell;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemBeingSold() {
        return itemBeingSold;
    }

    public double getItemPrice() {
        return itemPrice;
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
