package dev.cadindie;

import me.judge.nativebind.GenerateJNIWrapper;
import me.judge.nativebind.GenerateNativeWrapper;

public enum SortMethod {
    @GenerateNativeWrapper(prefix = "smmml")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge")
    RELEVANCE("relevance", "2", "recent_downloads", null),
    @GenerateNativeWrapper(prefix = "smmml")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge")
    DOWNLOADS("downloads", "6", "downloads", "downloads"),
    @GenerateNativeWrapper(prefix = "smmml")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge")
    RATINGS("follows", "12", "stars", null),
    @GenerateNativeWrapper(prefix = "smmml")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge")
    LAST_UPDATED("updated", "3", "updated", null),
    @GenerateNativeWrapper(prefix = "smmml")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge")
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
