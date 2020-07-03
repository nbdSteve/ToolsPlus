package gg.steve.mc.tp.tool.utils;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.utils.ItemBuilderUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LoreUpdaterUtil {

    public static ItemStack updateLore(NBTItem item, String tag, int update, String current, String replacement, int peak) {
        item.setInteger("tools+." + tag, update);
        item.setInteger("tools+.peak-" + tag, peak);
        ItemBuilderUtil builder = new ItemBuilderUtil(item.getItem());
        if (builder.getItemMeta().getDisplayName().contains(current)) {
            builder.addName(builder.getItemMeta().getDisplayName().replace(current, replacement));
        }
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(current)) {
                String line = lore.get(i).replace(current, replacement);
                lore.set(i, line);
            }
        }
        builder.setLore(lore);
        item.getItem().setItemMeta(builder.getItemMeta());
        return item.getItem();
    }

    public static ItemStack updateLore(NBTItem item, String tag, int update, String current, String replacement) {
        item.setInteger("tools+." + tag, update);
        ItemBuilderUtil builder = new ItemBuilderUtil(item.getItem());
        if (builder.getItemMeta().getDisplayName().contains(current)) {
            builder.addName(builder.getItemMeta().getDisplayName().replace(current, replacement));
        }
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(current)) {
                String line = lore.get(i).replace(current, replacement);
                lore.set(i, line);
            }
        }
        builder.setLore(lore);
        item.getItem().setItemMeta(builder.getItemMeta());
        return item.getItem();
    }
}
