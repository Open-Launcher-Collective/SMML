package dev.cadindie.sources.modrinth;

import dev.cadindie.Loader;
import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceType;
import dev.cadindie.ResourceVersion;
import dev.cadindie.sources.Source;

public class ModrinthResult implements ResourceResult {
    private final String title;
    private final String project_id;
    private final String slug;
    private final String[] versions;
    private final String description;
    private final Loader[] loaders;
    private final String project_type;
    private final String icon_url;
    private final String downloads;
    private final String author;

    public ModrinthResult(String title, String projectId, String slug, String[] versions, String description, Loader[] loaders, String project_type, String icon_url, String downloads, String author) {
        this.title = title;
        project_id = projectId;
        this.slug = slug;
        this.versions = versions;
        this.description = description;
        this.loaders = loaders;
        this.project_type = project_type;
        this.icon_url = icon_url;
        this.downloads = downloads;
        this.author = author;
    }

    @Override
    public String name() {
        return title;
    }

    @Override
    public String id() {
        return project_id;
    }

    @Override
    public String slug() {
        return slug;
    }

    @Override
    public String[] mcVersions() {
        return versions;
    }

    @Override
    public ResourceVersion[] resourceVersions() {
        Modrinth mr = new Modrinth();
        return mr.getProjectVersions(project_id).join();
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Loader[] loaders() {
        return loaders;
    }

    @Override
    public Source source() {
        return Source.MODRINTH;
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.fromString(project_type);
    }

    @Override
    public String iconUrl() {
        return icon_url;
    }

    @Override
    public String downloads() {
        return downloads;
    }

    @Override
    public String author() {
        return author;
    }
}
