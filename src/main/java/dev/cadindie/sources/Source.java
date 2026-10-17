package dev.cadindie.sources;

import dev.cadindie.sources.curseforge.CurseForge;
import dev.cadindie.sources.hangar.Hangar;
import dev.cadindie.sources.modrinth.Modrinth;
import dev.cadindie.sources.spiget.Spiget;
import me.judge.nativebind.GenerateJNIWrapper;
import me.judge.nativebind.GenerateNativeWrapper;

public enum Source {
    @GenerateNativeWrapper(prefix = "smmml", name = "sourceModrinth")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sourceModrinth")
    MODRINTH("Modrinth"),
    @GenerateNativeWrapper(prefix = "smmml", name = "sourceCurseForge")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sourceCurseForge")
    CURSEFORGE("CurseForge"),
    @GenerateNativeWrapper(prefix = "smmml", name = "sourceHangar")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sourceHangar")
    HANGAR("Hangar"),
    @GenerateNativeWrapper(prefix = "smmml", name = "sourceSpiget")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sourceSpiget")
    SPIGET("Spiget"),
    @GenerateNativeWrapper(prefix = "smmml", name = "sourceUnknown")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sourceUnknown")
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
