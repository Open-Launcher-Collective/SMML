package dev.cadindie.utils;

import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import okio.BufferedSource;
import okio.Okio;

import java.io.*;

public class JSONUtils {
    public static final Moshi moshi = new Moshi.Builder().build();

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        try {
            return moshi.adapter(clazz).fromJson(json);
        } catch (IOException e) {
            throw new IOException("Failed to parse JSON from String", e);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> clazz) {
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        try (JsonReader reader = JsonReader.of(source)) {
            return moshi.adapter(clazz).fromJson(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON from InputStream", e);
        }
    }
}
