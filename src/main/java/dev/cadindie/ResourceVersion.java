package dev.cadindie;

public interface ResourceVersion {
    String id();
    String version();
    String versionName();
    String changelog();
    String[] mcVersions();
    Loader[] loaders();
    ResourceFile[] files();
    ResourceDependency[] dependencies();

    // TODO: Add environment_type and other missing info
}
