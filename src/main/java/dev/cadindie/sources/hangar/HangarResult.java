package dev.cadindie.sources.hangar;

import dev.cadindie.Loader;
import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceType;
import dev.cadindie.ResourceVersion;
import dev.cadindie.sources.Source;
import dev.cadindie.sources.hangar.meta.HangarStats;

public class HangarResult implements ResourceResult {
    private final String name;
    private final int id;
    private final String slug;
    private final String[] versions;
    private final String description;
    private final Loader[] loaders;
    private final HangarStats stats;
    private final String avatarUrl;
    private final String[] memberNames;

    public HangarResult(String name, int id, String slug, String[] versions, String description, Loader[] loaders, HangarStats stats, String avatarUrl, String[] memberNames) {
        this.name = name;
        this.id = id;
        this.slug = slug;
        this.versions = versions;
        this.description = description;
        this.loaders = loaders;
        this.stats = stats;
        this.avatarUrl = avatarUrl;
        this.memberNames = memberNames;
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
    public String iconUrl() {
        return avatarUrl;
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
        Hangar hangar = new Hangar();
        return hangar.getProjectVersions(String.valueOf(id)).join();
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
        return Source.HANGAR;
    }

    @Override
    public String author() {
        StringBuilder sb = new StringBuilder();
        for (String memberName : memberNames) {
            sb.append(memberName).append(", ");
        }

        return !sb.isEmpty() ? sb.substring(0, sb.length() - 2) : "";
    }

    @Override
    public String downloads() {
        return String.valueOf(stats.downloads());
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.PLUGIN;
    }
}
