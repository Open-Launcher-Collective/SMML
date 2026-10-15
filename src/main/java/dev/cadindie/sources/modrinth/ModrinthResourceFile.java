package dev.cadindie.sources.modrinth;

import dev.cadindie.ResourceFile;

public class ModrinthResourceFile implements ResourceFile {
    private final String id;
    private final String url;
    private final String filename;
    private final Boolean primary;
    private final Long size;

    public ModrinthResourceFile(String id, String url, String filename, Boolean primary, Long size) {
        this.id = id;
        this.url = url;
        this.filename = filename;
        this.primary = primary;
        this.size = size;
    }

    @Override
    public String downloadUrl() {
        return url;
    }

    @Override
    public long fileSize() {
        return size;
    }

    @Override
    public String fileName() {
        return filename;
    }
}
