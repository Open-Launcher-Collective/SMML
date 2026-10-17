package dev.cadindie;

import me.judge.nativebind.GenerateJNIWrapper;
import me.judge.nativebind.GenerateNativeWrapper;

public enum ResourceType {
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypeMod")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypeMod")
    MOD("mod"),
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypePlugin")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypePlugin")
    PLUGIN("plugin"),
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypeDatapack")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypeDatapack")
    DATAPACK("datapack"),
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypeResourcePack")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypeResourcePack")
    RESOURCEPACK("resourcepack"),
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypeWorld")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypeWorld")
    WORLD("world"),
    @GenerateNativeWrapper(prefix = "smmml", name = "resourceTypeUnknown")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "resourceTypeUnknown")
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
