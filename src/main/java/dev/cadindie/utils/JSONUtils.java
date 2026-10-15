package dev.cadindie.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONUtils {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static void fromObject(Object obj, Path outPath) throws IOException {
        Files.createDirectories(outPath.getParent());
        Files.write(outPath, GSON.toJson(obj).getBytes());
    }

/*    public static <T> T fromFileJson(Path json, Class<T> clazz) throws IOException, JsonSyntaxException {
        if(!Files.exists(json)) {
            throw new IOException(json + " does not exist");
        }
        return GSON.fromJson(ConnectionUtil.readerToString(new BufferedReader(new FileReader(json.toFile()))), clazz);
    }*/
}
