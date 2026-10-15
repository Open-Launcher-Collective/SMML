package dev.cadindie.sources.curseforge;

import dev.cadindie.Loader;
import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceType;
import dev.cadindie.ResourceVersion;
import dev.cadindie.sources.Source;
import dev.cadindie.sources.curseforge.meta.CurseForgeAuthors;
import dev.cadindie.sources.curseforge.meta.CurseForgeLogo;

public class CurseForgeResult implements ResourceResult {
    private final String name;
    private final long id;
    private final String slug;
    private final String[] versions;
    private final CurseForgeLogo logo;
    private final String description;
    private final Loader[] loaders;
    private final ResourceType resourceType;
    private final int downloadCount;
    private final CurseForgeAuthors[] authors;

    public CurseForgeResult(String name, long id, String slug, String[] versions, CurseForgeLogo logo, String description, Loader[] loaders, ResourceType resourceType, int downloadCount, CurseForgeAuthors[] authors) {
        this.name = name;
        this.id = id;
        this.slug = slug;
        this.versions = versions;
        this.logo = logo;
        this.description = description;
        this.loaders = loaders;
        this.resourceType = resourceType;
        this.downloadCount = downloadCount;
        this.authors = authors;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String id() {
        return String.valueOf(id);
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
        CurseForge cf = new CurseForge();
        return cf.getProjectVersions(String.valueOf(id)).join();
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
        return Source.CURSEFORGE;
    }

    @Override
    public ResourceType resourceType() {
        return resourceType;
    }

    @Override
    public String iconUrl() {
        return logo.url();
    }

    @Override
    public String downloads() {
        return String.valueOf(downloadCount);
    }

    @Override
    public String author() {
        StringBuilder sb = new StringBuilder();
        for (CurseForgeAuthors authors : authors) {
            sb.append(authors.name()).append(", ");
        }
        return !sb.isEmpty() ? sb.substring(0, sb.length() - 2) : "";
    }
}
