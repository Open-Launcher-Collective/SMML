package dev.cadindie.sources.curseforge;

import dev.cadindie.ResourceResult;
import dev.cadindie.ResourceVersion;
import dev.cadindie.SMML;
import dev.cadindie.sources.SearchRequest;
import dev.cadindie.sources.SourceInterface;
import dev.cadindie.sources.curseforge.meta.CurseForgeSearchResponse;
import dev.cadindie.sources.curseforge.meta.CurseForgeVersionResponse;
import dev.cadindie.utils.JSONUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

// TODO: Figure out a way to handle CF tokens
public class CurseForge implements SourceInterface {
    @Override
    public String apiVersion() {
        return "1.0.0";
    }

    @Override
    public CompletableFuture<ResourceResult[]> search(SearchRequest searchRequest) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.curseforge.com")
                .addPathSegment("v1")
                .addPathSegment("mods")
                .addPathSegment("search")
                .addQueryParameter("gameId", String.valueOf(432))
                .addQueryParameter("searchFilter", searchRequest.query)
                .addQueryParameter("sortField", searchRequest.sortMethod.curseforgeName)
                .addQueryParameter("pageSize", String.valueOf(searchRequest.limit))
                .addQueryParameter("sortOrder", searchRequest.sortOrder.curseforgeName)
                .build();

        Request request = new Request.Builder().url(url).addHeader("x-api-key", System.getenv("CFToken")).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            CurseForgeSearchResponse searchResponse = JSONUtils.fromJson(response.body().string(), CurseForgeSearchResponse.class);
            return CompletableFuture.completedFuture(searchResponse.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<ResourceVersion[]> getProjectVersions(String projectId) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.curseforge.com")
                .addPathSegment("v1")
                .addPathSegment("mods")
                .addPathSegment(projectId)
                .addPathSegment("files")
                .build();

        Request request = new Request.Builder().url(url).addHeader("x-api-key", System.getenv("CFToken")).build();

        try (Response response = SMML.httpClient.newCall(request).execute()) {
            CurseForgeVersionResponse versionResponse = JSONUtils.fromJson(response.body().string(), CurseForgeVersionResponse.class);
            return CompletableFuture.completedFuture(versionResponse.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
