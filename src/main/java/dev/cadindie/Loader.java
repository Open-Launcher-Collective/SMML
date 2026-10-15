package dev.cadindie;

public enum Loader {
    FABRIC("Fabric" ),
    FORGE("Forge"),
    NEOFORGE("NeoForge"),
    QUILT("Quilt"),
    PAPER("Paper"),
    BUKKIT("Bukkit"),
    SPIGOT("Spigot"),
    FOLIA("Folia"),
    PURPUR("Purpur"),
    SPONGE("Sponge"),

    // Platforms according to Modrinth
    WATERFALL("Waterfall"),
    VELOCITY("Velocity"),
    BUNGEECORD("BungeeCord"),
    GEYSER("Geyser"),

    UNKNOWN("Unknown");

    public final String name;
    Loader(String name) {
        this.name = name;
    }

    public static Loader fromString(String name) {
        for (Loader loader : Loader.values()) {
            if (loader.name.equalsIgnoreCase(name)) {
                return loader;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return this.name;
    }
}
