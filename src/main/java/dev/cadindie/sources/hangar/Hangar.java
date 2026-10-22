package dev.cadindie.sources.hangar;

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

public class Hangar implements SourceInterface {
    @Override
    public String apiVersion() {
        return "1.0.0";
    }

    @Override
    public CompletableFuture<ResourceResult[]> search(SearchRequest searchRequest) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("hangar.papermc.io")
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("projects")
                .addQueryParameter("query", searchRequest.query)
                .addQueryParameter("sort", searchRequest.sortMethod.hangarName)
                .addQueryParameter("offset", String.valueOf(((searchRequest.page - 1) * searchRequest.limit)))
                .addQueryParameter("limit", String.valueOf(searchRequest.limit))
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            HangarSearchResponse searchResponse = JSONUtils.fromJson(response.body().string(), HangarSearchResponse.class);
            return CompletableFuture.completedFuture(searchResponse.getResult());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<ResourceVersion[]> getProjectVersions(String projectSlug) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("hangar.papermc.io")
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("projects")
                .addPathSegment(projectSlug)
                .addPathSegment("versions")
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            return CompletableFuture.completedFuture(JSONUtils.fromJson(response.body().string(), ResourceVersion[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
