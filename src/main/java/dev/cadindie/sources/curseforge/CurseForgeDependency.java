package dev.cadindie.sources.curseforge;

import dev.cadindie.DependencyType;
import dev.cadindie.ResourceDependency;

import java.util.Optional;

public class CurseForgeDependency implements ResourceDependency {
    private final int modId;
    private final int relationType;

    public CurseForgeDependency(int modId, int relationType) {
        this.modId = modId;
        this.relationType = relationType;
    }

    @Override
    public String id() {
        return String.valueOf(modId);
    }

    @Override
    public Optional<String> versionId() {
        return Optional.empty();
    }

    @Override
    public DependencyType type() {
        return switch (relationType) {
            case 1 -> DependencyType.EMBEDDED;
            case 2 -> DependencyType.OPTIONAL;
            case 3 -> DependencyType.REQUIRED;
            case 4 -> DependencyType.TOOL;
            case 5 -> DependencyType.INCOMPATIBLE;
            case 6 -> DependencyType.INCLUDED;
            default -> DependencyType.UNKNOWN;
        };
    }
}
