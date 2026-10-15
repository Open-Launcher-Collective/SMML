package dev.cadindie.sources.modrinth;

import dev.cadindie.DependencyType;
import dev.cadindie.ResourceDependency;

import java.util.Optional;

public class ModrinthDependency implements ResourceDependency {
    private final String version_id;
    private final String project_id;
    private final String dependency_type;

    public ModrinthDependency(String version_id, String project_id, String dependency_type) {
        this.version_id = version_id;
        this.project_id = project_id;
        this.dependency_type = dependency_type;
    }

    @Override
    public String id() {
        return project_id;
    }

    @Override
    public Optional<String> versionId() {
        return Optional.of(version_id);
    }

    @Override
    public DependencyType type() {
        switch (dependency_type) {
            case "required" -> {
                return DependencyType.REQUIRED;
            }
            case "optional" -> {
                return DependencyType.OPTIONAL;
            }
            case "incompatible" -> {
                return DependencyType.INCOMPATIBLE;
            }
            case "embedded" -> {
                return DependencyType.EMBEDDED;
            }
            default -> {
                return DependencyType.UNKNOWN;
            }
        }
    }
}
