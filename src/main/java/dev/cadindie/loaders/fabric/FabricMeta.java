package dev.cadindie.loaders.fabric;

import dev.cadindie.Resource;
import dev.cadindie.ResourceType;
import dev.cadindie.utils.JSONUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FabricMeta implements Resource {
    String name;
    String id;

    public FabricMeta(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static Resource getResource(ZipFile jarFile, ZipEntry entry) {
        try (InputStream is = jarFile.getInputStream(entry)) {
            return JSONUtils.fromJson(is, FabricMeta.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read fabric.mod.json from jar file", e);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.MOD;
    }
}
