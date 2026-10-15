package dev.cadindie;

import java.util.Optional;

public interface ResourceDependency {
    String id();
    Optional<String> versionId();
    DependencyType type();
}
