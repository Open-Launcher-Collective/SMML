package dev.cadindie.sources;

import dev.cadindie.sources.curseforge.CurseForge;
import dev.cadindie.sources.hangar.Hangar;
import dev.cadindie.sources.modrinth.Modrinth;
import dev.cadindie.sources.spiget.Spiget;

public enum Source {
    MODRINTH("Modrinth"),
    CURSEFORGE("CurseForge"),
    HANGAR("Hangar"),
    SPIGET("Spiget"),
    UNKNOWN("Unknown");

    public final String name;
    Source(String name) {
        this.name = name;
    }

    public static Source fromString(String name) {
        for (Source Source : Source.values()) {
            if (Source.name.equalsIgnoreCase(name)) {
                return Source;
            }
        }
        return UNKNOWN;
    }

    public String toString() {
        return this.name;
    }

    public SourceInterface getSourceInterface() {
        return switch (this) {
            case MODRINTH -> new Modrinth();
            case CURSEFORGE -> new CurseForge();
            case HANGAR -> new Hangar();
            case SPIGET -> new Spiget();
            default -> throw new IllegalArgumentException("Unknown source: " + this.name);
        };
    }
}
