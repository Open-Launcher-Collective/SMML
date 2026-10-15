package dev.cadindie.sources;

import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceVersion;

import java.util.concurrent.CompletableFuture;

public interface SourceInterface {
    String apiVersion();
    CompletableFuture<ResourceResult[]> search(SearchRequest searchRequest);
    CompletableFuture<ResourceVersion[]> getProjectVersions(String projectSlug);
}
