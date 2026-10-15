package dev.cadindie.sources.modrinth;

import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceVersion;
import dev.cadindie.SMML;
import dev.cadindie.sources.SearchRequest;
import dev.cadindie.sources.SourceInterface;
import dev.cadindie.utils.JSONUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Modrinth implements SourceInterface {
    @Override
    public String apiVersion() {
        return "v2.7.0/366f528";
    }

    @Override
    public CompletableFuture<ResourceResult[]> search(SearchRequest searchRequest) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.modrinth.com")
                .addPathSegment("v2")
                .addPathSegment("search")
                .addQueryParameter("query", searchRequest.query)
                //.addQueryParameter("facets", searchRequest.facets)
                .addQueryParameter("index", searchRequest.sortMethod.modrinthName)
                .addQueryParameter("limit", String.valueOf(searchRequest.limit))
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            ModrinthSearchResponse searchResponse = JSONUtils.fromJson(response.body().string(), ModrinthSearchResponse.class);
            return CompletableFuture.completedFuture(searchResponse.getHits());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<ResourceVersion[]> getProjectVersions(String projectSlug) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.modrinth.com")
                .addPathSegment("v2")
                .addPathSegment("project")
                .addPathSegment(projectSlug)
                .addPathSegment("version")
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            return CompletableFuture.completedFuture(JSONUtils.fromJson(response.body().string(), ModrinthResourceVersion[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
