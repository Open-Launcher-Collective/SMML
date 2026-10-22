package dev.cadindie.sources.spiget;

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

public class Spiget implements SourceInterface {
    @Override
    public String apiVersion() {
        return "2.0.0";
    }

    @Override
    public CompletableFuture<ResourceResult[]> search(SearchRequest searchRequest) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spiget.org")
                .addPathSegment("v2")
                .addPathSegment("search")
                .addPathSegment("resources")
                .addPathSegment(searchRequest.query)
                .addQueryParameter("sort", searchRequest.sortMethod.spigetName)
                .addQueryParameter("size", String.valueOf(searchRequest.limit))
                .addQueryParameter("page", String.valueOf(searchRequest.page))
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            return CompletableFuture.completedFuture(JSONUtils.fromJson(response.body().string(), SpigetResult[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<ResourceVersion[]> getProjectVersions(String projectSlug) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.spiget.org")
                .addPathSegment("v2")
                .addPathSegment("resources")
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
