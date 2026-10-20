package dev.cadindie.loaders.forge;

import dev.cadindie.Resource;
import dev.cadindie.ResourceType;
import org.tomlj.Toml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ForgeMeta implements Resource {
    String displayName;
    String modId;

    public ForgeMeta(String displayName, String modId) {
        this.displayName = displayName;
        this.modId = modId;
    }

    public static Resource getResource(ZipFile zipFile, ZipEntry entry) {
        try (InputStream is = zipFile.getInputStream(entry)) {
            String meta = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return Toml.parseAs(meta, ForgeMeta.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read META-INF/mods.toml from jar file", e);
        }
    }

    @Override
    public String name() {
        return displayName;
    }

    @Override
    public String id() {
        return modId;
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.MOD;
    }
}
