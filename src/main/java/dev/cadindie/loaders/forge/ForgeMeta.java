package dev.cadindie.loaders.forge;

import com.moandjiezana.toml.Toml;
import dev.cadindie.Resource;
import dev.cadindie.ResourceType;

import java.io.IOException;
import java.io.InputStream;
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
            Toml toml = new Toml().read(is);
            Toml firstMod = toml.getTables("mods").getFirst();
            return firstMod.to(ForgeMeta.class);
        } catch (IOException | NullPointerException | IndexOutOfBoundsException e) {
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
