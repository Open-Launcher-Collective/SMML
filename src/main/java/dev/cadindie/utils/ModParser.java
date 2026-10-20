package dev.cadindie.utils;

import dev.cadindie.Resource;
import dev.cadindie.loaders.fabric.FabricMeta;
import dev.cadindie.loaders.forge.ForgeMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModParser {
    public static Resource[] parseFolder(String directoryPath) {
        File folder = new File(directoryPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s does not exist or isn't a directory", directoryPath));
        }

        List<Resource> results = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(folder.toPath())) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".jar"))
                    .forEach(path -> results.add(parseJar(path.toFile())));
        } catch (IOException e) {
            System.out.print("Failed to parse folder: " + directoryPath + " due to exception: " + e.getMessage());
        }

        return results.toArray(new Resource[0]);
    }

    public static Resource parseJar(File jarFile) {
        try (ZipFile zipFile = new ZipFile(jarFile)) {
            for (ZipEntry entry : java.util.Collections.list(zipFile.entries())) {
                switch (entry.getName()) {
                    case "fabric.mod.json": return FabricMeta.getResource(zipFile, entry);
                    case "META-INF/mods.toml": return ForgeMeta.getResource(zipFile, entry);
                    default: // TODO: NeoForge, Quilt, Paper, etc
                }
            }
        } catch (IOException e) {
            System.out.print("Failed to parse jar file: " + jarFile.getName() + " due to exception: " + e.getMessage());
        }

        return null;
    }
}
