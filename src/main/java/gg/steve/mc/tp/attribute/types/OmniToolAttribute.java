package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.framework.yml.Files;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class OmniToolAttribute extends AbstractToolAttribute {
    //Store the blocks to be broken by shovels
    private static ArrayList<String> spadeBlockTypes;
    //Store the blocks to be broken by pickaxes
    private static ArrayList<String> pickaxeBlockTypes;
    //Store the blocks to be broken by axes
    private static ArrayList<String> axeBlockTypes;

    /**
     * Method to change the tool that the player is currently holding, updates in their hand
     *
     * @param block  Block, the block being broken
     * @param player Player, the player breaking
     */
    public static void changeToolType(Block block, Player player) {
        String itemInHand = player.getItemInHand().getType().toString();
        String[] materialType;
        try {
            materialType = itemInHand.split("_");
        } catch (Exception notATool) {
            return;
        }
        if (spadeBlockTypes.contains(block.getType().toString())
                && (!player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_SPADE"))
                || !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_SHOVEL")))) {
            try {
                player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_SPADE"));
            } catch (Exception e) {
                player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_SHOVEL"));
            }
        }
        if (pickaxeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_PICKAXE"))) {
            player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_PICKAXE"));
        }
        if (axeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_AXE"))) {
            player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_AXE"));
        }
    }

    /**
     * Method to load the blocks broken by respective tools from the configuration
     */
    public static void loadOmniConfig() {
        spadeBlockTypes = new ArrayList<>();
        pickaxeBlockTypes = new ArrayList<>();
        axeBlockTypes = new ArrayList<>();
        for (String blockType : Files.OMNI_CONFIG.get().getStringList("shovel-blocks")) {
            spadeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : Files.OMNI_CONFIG.get().getStringList("pickaxe-blocks")) {
            pickaxeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : Files.OMNI_CONFIG.get().getStringList("axe-blocks")) {
            axeBlockTypes.add(blockType.toUpperCase());
        }
    }

    /**
     * Clears the tools blocks lists
     */
    public static void shutdown() {
        if (spadeBlockTypes != null && !spadeBlockTypes.isEmpty()) spadeBlockTypes.clear();
        if (pickaxeBlockTypes != null && !pickaxeBlockTypes.isEmpty()) pickaxeBlockTypes.clear();
        if (axeBlockTypes != null && !axeBlockTypes.isEmpty()) axeBlockTypes.clear();
    }

    public OmniToolAttribute(String updateString) {
        super(ToolAttributeType.OMNI, updateString);
    }

    @Override
    public boolean doIncrease(Player player, PlayerTool tool, AbstractCurrency currency, int amount, double cost) {
        return false;
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        return false;
    }

    @Override
    public boolean isOnCooldown(Player player, PlayerTool tool) {
        return false;
    }
}
