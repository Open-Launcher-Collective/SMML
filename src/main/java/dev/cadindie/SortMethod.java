package dev.cadindie;

public enum SortMethod {
    RELEVANCE("relevance", "2", "recent_downloads", null),
    DOWNLOADS("downloads", "6", "downloads", "downloads"),
    RATINGS("follows", "12", "stars", null),
    LAST_UPDATED("updated", "3", "updated", null),
    NEWEST("newest", "11", "newest", null);

    public final String modrinthName;
    public final String curseforgeName;
    public final String hangarName;
    public final String spigetName;
    SortMethod(String modrinthName, String curseforgeName, String hangarName, String spigetName) {
        this.modrinthName = modrinthName;
        this.curseforgeName = curseforgeName;
        this.hangarName = hangarName;
        this.spigetName = spigetName;
    }
}
