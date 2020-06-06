package gg.steve.mc.tp.attribute;

import java.util.HashMap;
import java.util.Map;

public class ToolAttributeManager {
    private Map<ToolAttributeType, AbstractToolAttribute> attributes;

    public ToolAttributeManager() {
        this.attributes = new HashMap<>();
    }

    public boolean addToolAttribute(AbstractToolAttribute attribute) {
        if (this.attributes == null) return false;
        return this.attributes.put(attribute.getType(), attribute) != null;
    }

    public boolean isAttributeEnabled(ToolAttributeType type) {
        if (this.attributes == null || this.attributes.isEmpty()) return false;
        return this.attributes.containsKey(type);
    }

    public AbstractToolAttribute getAttribute(ToolAttributeType type) {
        if (this.attributes == null || this.attributes.isEmpty()) return null;
        return this.attributes.get(type);
    }
}
