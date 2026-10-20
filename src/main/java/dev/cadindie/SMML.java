package dev.cadindie;

import dev.cadindie.sources.SearchRequest;
import dev.cadindie.sources.Source;
import dev.cadindie.utils.ModParser;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SMML {
    public static OkHttpClient httpClient = new OkHttpClient();

    /**
     * Get the version of the SMML implementation.
     * @return the version of the SMML implementation
     */
    public static String getVersion() {
        return "1.0.0";
    }

    /**
     * Get the API version of the given source.
     * @param source the source to get the API version from
     * @return the API version of the given source
     */
    public static String getSourceApiVersion(Source source) {
        return source.getSourceInterface().apiVersion();
    }

    /**
     * Search for resources matching the given search request.
     * @param request the search request
     * @param sources the sources to search in
     * @return a CompletableFuture that will complete with an array of ResourceResult objects
     */
    public static CompletableFuture<ResourceResult[]> search(SearchRequest request, Source... sources) {
        List<CompletableFuture<ResourceResult[]>> futures = new ArrayList<>();
        if (sources.length == 0) {
            throw new IllegalArgumentException("At least one source must be provided for the search.");
        }

        for (Source source : sources) {
            futures.add(source.getSourceInterface().search(request));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<ResourceResult> results = new ArrayList<>();
                    for (CompletableFuture<ResourceResult[]> future : futures) {
                        try {
                            ResourceResult[] sourceResults = future.get();
                            if (sourceResults != null) {
                                Collections.addAll(results, sourceResults);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to search sources due to exception: ", e);
                        }
                    }
                    return results.toArray(new ResourceResult[0]);
                });
    }

    /**
     * Get the versions of a project from a given source.
     * @param projectId the id of the project to get versions for
     * @param source the source to get the project versions from
     * @return a CompletableFuture that will complete with an array of ResourceVersion objects
     */
    // TODO: Add builder for this method to allow for filtering by game version, loader, etc.
    public static CompletableFuture<ResourceVersion[]> getProjectVersions(String projectId, Source source) {
        return source.getSourceInterface().getProjectVersions(projectId);
    }

    /**
     * Parse the mods folder of a given instance directory and return an array of Resource objects representing the mods found.
     * @param modsFolder the path to the mods folder to parse
     * @return an array of Resource objects representing the mods found in the given mods folder
     */
    public static Resource[] parseModsFolder(String modsFolder) {
        return ModParser.parseFolder(modsFolder);
    }
}
