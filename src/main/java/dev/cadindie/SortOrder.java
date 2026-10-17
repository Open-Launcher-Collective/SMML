package dev.cadindie;

import me.judge.nativebind.GenerateJNIWrapper;
import me.judge.nativebind.GenerateNativeWrapper;

public enum SortOrder {
    @GenerateNativeWrapper(prefix = "smmml", name = "sortOrderAscending")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sortOrderAscending")
    ASCENDING("asc"),
    @GenerateNativeWrapper(prefix = "smmml", name = "sortOrderDescending")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "sortOrderDescending")
    DESCENDING("desc");

    public final String curseforgeName;

    SortOrder(String curseforgeName) {
        this.curseforgeName = curseforgeName;
    }
}
