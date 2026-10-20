package dev.cadindie.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONUtils {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> clazz) {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return GSON.fromJson(reader, clazz);
        } catch (JsonSyntaxException | IOException e) {
            throw new RuntimeException("Failed to parse JSON from InputStream", e);
        }
    }

    public static void fromObject(Object obj, Path outPath) throws IOException {
        Files.createDirectories(outPath.getParent());
        Files.write(outPath, GSON.toJson(obj).getBytes());
    }
}
