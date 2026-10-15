package dev.cadindie.sources.modrinth;

import dev.cadindie.Loader;
import dev.cadindie.ResourceDependency;
import dev.cadindie.ResourceFile;
import dev.cadindie.ResourceVersion;

import java.util.ArrayList;

public class ModrinthResourceVersion implements ResourceVersion {
    private final String version_number;
    private final String version_name;
    private final String changelog;
    private final String[] game_versions;
    private final String[] loaders;
    private final ModrinthResourceFile[] files;
    private final String id;
    private final ModrinthDependency[] dependencies;

    public ModrinthResourceVersion(String version_number, String version_name, String changelog, String[] game_versions, String[] loaders, ModrinthResourceFile[] files, String id, ModrinthDependency[] dependencies) {
        this.version_number = version_number;
        this.version_name = version_name;
        this.changelog = changelog;
        this.game_versions = game_versions;
        this.loaders = loaders;
        this.files = files;
        this.id = id;
        this.dependencies = dependencies;
    }

    @Override
    public String version() {
        return version_number;
    }

    @Override
    public String versionName() {
        return version_name;
    }

    @Override
    public String changelog() {
        return changelog;
    }

    @Override
    public String[] mcVersions() {
        return game_versions;
    }

    @Override
    public Loader[] loaders() {
        ArrayList<Loader> result = new ArrayList<>();
        for (String loader : loaders) {
            result.add(Loader.fromString(loader));
        }

        return result.toArray(new Loader[0]);
    }

    @Override
    public ResourceFile[] files() {
        return files;
    }

    @Override
    public ResourceDependency[] dependencies() {
        return dependencies;
    }

    @Override
    public String id() {
        return id;
    }
}
