package dev.cadindie;

import dev.cadindie.sources.Source;

public interface ResourceResult extends Resource {
    String name();
    String id();
    String iconUrl();
    String slug();
    String[] mcVersions();
    ResourceVersion[] resourceVersions();
    String description();
    Loader[] loaders();
    Source source();
    String author();
    String downloads();
    ResourceType resourceType();
}
