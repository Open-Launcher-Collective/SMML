package dev.cadindie.sources.modrinth;

public class ModrinthSearchResponse {
    private ModrinthResult[] hits;
    private int offset;
    private int limit;
    private int total_hits;

    public ModrinthSearchResponse() {
    }

    public ModrinthResult[] getHits() {
        return hits;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalHits() {
        return total_hits;
    }
}