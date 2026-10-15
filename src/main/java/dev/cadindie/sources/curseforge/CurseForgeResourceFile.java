package dev.cadindie.sources.curseforge;

import dev.cadindie.ResourceFile;

public class CurseForgeResourceFile implements ResourceFile {
    private final String fileName;
    private final String downloadUrl;
    private final long fileSizeOnDisk;

    public CurseForgeResourceFile(String fileName, String downloadUrl, long fileSizeOnDisk) {
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
        this.fileSizeOnDisk = fileSizeOnDisk;
    }

    public CurseForgeResourceFile[] toArray() {
        return new CurseForgeResourceFile[] { this };
    }

    @Override
    public String fileName() {
        return fileName;
    }

    @Override
    public String downloadUrl() {
        return downloadUrl;
    }

    @Override
    public long fileSize() {
        return fileSizeOnDisk;
    }
}
