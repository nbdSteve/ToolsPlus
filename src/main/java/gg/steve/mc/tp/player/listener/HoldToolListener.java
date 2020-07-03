package gg.steve.mc.tp.player.listener;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class HoldToolListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHold(PlayerItemHeldEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        NBTItem newItem, oldItem;
        if (event.getPlayer().getInventory().getItem(event.getPreviousSlot()) != null
                && !event.getPlayer().getInventory().getItem(event.getPreviousSlot()).getType().equals(Material.AIR)) {
            oldItem = new NBTItem(event.getPlayer().getInventory().getItem(event.getPreviousSlot()));
            if (GetToolHoldingUtil.isHoldingTool(oldItem)) {
                PlayerToolManager.removeToolPlayer(player.getUniqueId());
            }
        }
        if (event.getPlayer().getInventory().getItem(event.getNewSlot()) != null
                && !event.getPlayer().getInventory().getItem(event.getNewSlot()).getType().equals(Material.AIR)) {
            newItem = new NBTItem(event.getPlayer().getInventory().getItem(event.getNewSlot()));
            if (GetToolHoldingUtil.isHoldingTool(newItem)) {
                PlayerToolManager.updateToolType(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(newItem));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDrop(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        NBTItem oldItem;
        if (!event.getItemDrop().getItemStack().getType().equals(Material.AIR)) {
            oldItem = new NBTItem(event.getItemDrop().getItemStack());
            if (GetToolHoldingUtil.isHoldingTool(oldItem)) {
                PlayerToolManager.removeToolPlayer(player.getUniqueId());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerPickup(PlayerPickupItemEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        NBTItem item;
        if (!event.getItem().getItemStack().getType().equals(Material.AIR)) {
            item = new NBTItem(event.getItem().getItemStack());
            if (GetToolHoldingUtil.isHoldingTool(item)) {
                PlayerToolManager.updateToolType(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(item));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryRemove(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (!event.getCurrentItem().equals(player.getItemInHand())) return;
        NBTItem item = new NBTItem(player.getItemInHand());
        if (GetToolHoldingUtil.isHoldingTool(item)) {
            PlayerToolManager.removeToolPlayer(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryAdd(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getCursor() == null || event.getCursor().getType().equals(Material.AIR)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() != player.getInventory().getHeldItemSlot()) return;
        NBTItem item = new NBTItem(event.getCursor());
        if (GetToolHoldingUtil.isHoldingTool(item)) {
            PlayerToolManager.updateToolType(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(item));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryShift(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getClick().equals(ClickType.SHIFT_RIGHT) || event.getClick().equals(ClickType.SHIFT_LEFT)))
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) return;
        if (player.getInventory().firstEmpty() != player.getInventory().getHeldItemSlot()) return;
        NBTItem item = new NBTItem(event.getCurrentItem());
        if (GetToolHoldingUtil.isHoldingTool(item)) {
            PlayerToolManager.updateToolType(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(item));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryNum(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (!event.getClick().equals(ClickType.NUMBER_KEY)) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getHotbarButton() != player.getInventory().getHeldItemSlot()) return;
        NBTItem current, slot;
        if (player.getInventory().getItem(event.getHotbarButton()) != null && !player.getInventory().getItem(event.getHotbarButton()).getType().equals(Material.AIR)) {
            slot = new NBTItem(player.getInventory().getItem(event.getHotbarButton()));
            if (GetToolHoldingUtil.isHoldingTool(slot)) {
                PlayerToolManager.removeToolPlayer(player.getUniqueId());
            }
        }
        if (event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            current = new NBTItem(event.getCurrentItem());
            if (GetToolHoldingUtil.isHoldingTool(current)) {
                PlayerToolManager.updateToolType(player.getUniqueId(), GetToolHoldingUtil.getHoldingTool(current));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void death(PlayerDeathEvent event) {
        if (event.getKeepInventory()) return;
        if (event.getEntity().getItemInHand() == null || event.getEntity().getItemInHand().getType().equals(Material.AIR))
            return;
        NBTItem item = new NBTItem(event.getEntity().getItemInHand());
        if (GetToolHoldingUtil.isHoldingTool(item)) {
            PlayerToolManager.removeToolPlayer(event.getEntity().getUniqueId());
        }
    }

    @EventHandler
    public void clear(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/clear") ||
                event.getMessage().equalsIgnoreCase("/ci") ||
                event.getMessage().equalsIgnoreCase("/clearinventory")) {
            if (PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) {
                PlayerToolManager.removeToolPlayer(event.getPlayer().getUniqueId());
            }
        }
    }
}