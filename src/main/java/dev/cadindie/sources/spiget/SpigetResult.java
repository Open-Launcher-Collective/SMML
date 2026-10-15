package dev.cadindie.sources.spiget;

import dev.cadindie.Loader;
import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceType;
import dev.cadindie.ResourceVersion;
import dev.cadindie.sources.Source;
import dev.cadindie.sources.spiget.meta.SpigetIcon;

public class SpigetResult implements ResourceResult {
    private final String name;
    private final int id;
    private final String slug;
    private final String[] versions;
    private final String description;
    private final Loader[] loaders;
    private final int downloads;
    private final SpigetIcon icon;
    private final String contributors;

    public SpigetResult(String name, int id, String slug, String[] versions, String description, Loader[] loaders, int downloads, SpigetIcon icon, String contributors) {
        this.name = name;
        this.id = id;
        this.slug = slug;
        this.versions = versions;
        this.description = description;
        this.loaders = loaders;
        this.downloads = downloads;
        this.icon = icon;
        this.contributors = contributors;
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
        return icon.url();
    }

    @Override
    public String slug() {
        return slug;
    }

    @Override
    public String[] mcVersions() {
        return versions;
    }

    // TODO: Implement resourceVersions() method to fetch versions from Spiget API
    @Override
    public ResourceVersion[] resourceVersions() {
        return new ResourceVersion[0];
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
        return Source.SPIGET;
    }

    @Override
    public String downloads() {
        return String.valueOf(downloads);
    }

    @Override
    public String author() {
        return contributors;
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.PLUGIN;
    }
}
