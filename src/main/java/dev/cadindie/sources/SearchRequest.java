package dev.cadindie.sources;

import dev.cadindie.Loader;
import dev.cadindie.SortMethod;
import dev.cadindie.SortOrder;
import org.jetbrains.annotations.NotNull;

public class SearchRequest {
    public final String query;
    public final String[] gameVersions;
    public final Loader loader;
    public final int limit;
    public final SortMethod sortMethod;
    public final SortOrder sortOrder;

    private SearchRequest(Builder builder) {
        this.query = builder.query;
        this.gameVersions = builder.gameVersions;
        this.loader = builder.loader;
        this.limit = builder.limit;
        this.sortMethod = builder.sortMethod;
        this.sortOrder = builder.sortOrder;
    }

    public static class Builder {
        private String query;
        private String[] gameVersions;
        private Loader loader;
        private int limit = 10;
        private SortMethod sortMethod = SortMethod.RELEVANCE;
        private SortOrder sortOrder = SortOrder.DESCENDING;
        public Builder(@NotNull String query) {
            this.query = query;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder gameVersion(String ...versions) {
            this.gameVersions = versions;
            return this;
        }

        public Builder loader(Loader loader) {
            this.loader = loader;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder sortMethod(SortMethod sortMethod) {
            this.sortMethod = sortMethod;
            return this;
        }

        public Builder sortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public SearchRequest build() {
            if (query == null) {
                throw new RuntimeException("Query cannot be null");
            }

            if (limit < 1) {
                throw new RuntimeException("Limit must be greater than 0");
            }

            return new SearchRequest(this);
        }
    }
}
