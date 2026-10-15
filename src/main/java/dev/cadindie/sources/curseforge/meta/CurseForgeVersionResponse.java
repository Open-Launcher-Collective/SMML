package dev.cadindie.sources.curseforge.meta;

import dev.cadindie.sources.curseforge.CurseForgeResourceVersion;

public class CurseForgeVersionResponse {
    CurseForgeResourceVersion[] data;

    public CurseForgeVersionResponse() {}

    public CurseForgeResourceVersion[] getData() {
        return data;
    }
}
