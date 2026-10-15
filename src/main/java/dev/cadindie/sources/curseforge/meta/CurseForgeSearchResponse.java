package dev.cadindie.sources.curseforge.meta;

import dev.cadindie.sources.curseforge.CurseForgeResult;

public class CurseForgeSearchResponse {
    CurseForgeResult[] data;

    public CurseForgeSearchResponse() {}

    public CurseForgeResult[] getData() {
        return data;
    }
}
