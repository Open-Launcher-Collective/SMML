package dev.cadindie.sources.curseforge;

import dev.cadindie.Loader;
import dev.cadindie.ResourceDependency;
import dev.cadindie.ResourceFile;
import dev.cadindie.ResourceVersion;

public class CurseForgeResourceVersion implements ResourceVersion {
    private final int id;
    private final String displayName;
    private final String fileName;
    private final String downloadUrl;
    private final long fileSizeOnDisk;
    private final String[] gameVersions;
    private final CurseForgeDependency[] dependencies;

    public CurseForgeResourceVersion(int id, String displayName, String fileName, String downloadUrl, long fileSizeOnDisk, String[] gameVersions, CurseForgeDependency[] dependencies) {
        this.id = id;
        this.displayName = displayName;
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
        this.fileSizeOnDisk = fileSizeOnDisk;
        this.gameVersions = gameVersions;
        this.dependencies = dependencies;
    }

    @Override
    public String version() {
        return "";
    }

    @Override
    public String versionName() {
        return displayName;
    }

    @Override
    public String changelog() {
        return "";
    }

    // TODO: Separate loader/platform info and versions
    @Override
    public String[] mcVersions() {
        return gameVersions;
    }

    @Override
    public Loader[] loaders() {
        return new Loader[0];
    }

    @Override
    public ResourceFile[] files() {
        return new CurseForgeResourceFile(fileName, downloadUrl, fileSizeOnDisk).toArray();
    }

    @Override
    public String id() {
        return String.valueOf(id);
    }

    @Override
    public ResourceDependency[] dependencies() {
        return dependencies;
    }
}
