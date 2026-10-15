package dev.cadindie;

public enum ResourceType {
    MOD("mod"),
    PLUGIN("plugin"),
    DATAPACK("datapack"),
    RESOURCEPACK("resourcepack"),
    WORLD("world"),
    UNKNOWN("unknown");

    public final String name;
    ResourceType(String name) {
        this.name = name;
    }

    public static ResourceType fromString(String name) {
        for (ResourceType ResourceType : ResourceType.values()) {
            if (ResourceType.name.equalsIgnoreCase(name)) {
                return ResourceType;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return this.name;
    }
}
