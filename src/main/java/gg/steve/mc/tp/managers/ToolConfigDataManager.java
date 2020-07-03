package gg.steve.mc.tp.managers;

import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolConfigDataManager {
    private static Map<String, List<Material>> lists;

    private ToolConfigDataManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialise() {
        lists = new HashMap<>();
    }

    public static void addMaterialList(String moduleId, List<String> list) {
        if (lists != null && !lists.containsKey(moduleId))
            lists.put(moduleId, convertStringsToMaterials(list));
    }

    public static boolean queryMaterialList(String moduleId, Material check, boolean required) {
        if (lists == null || lists.isEmpty()) return required;
        if (!lists.containsKey(moduleId)) {
            LogUtil.warning("Tried to query material list for " + moduleId + ", but it was not in the map, returning default required.");
            return required;
        }
        return lists.get(moduleId).contains(check) == required;
    }

    public static void shutdown() {
        if (lists != null && !lists.isEmpty()) lists.clear();
    }

    public static List<Material> convertStringsToMaterials(List<String> list) {
        List<Material> materials = new ArrayList<>();
        for (String material : list) {
            try {
                materials.add(Material.getMaterial(material.toUpperCase()));
            } catch (Exception e) {
                LogUtil.warning("Unable to add material: " + material.toUpperCase() + ", to a list / map. Please change this item to its bukkit name.");
            }
        }
        return materials;
    }
}
