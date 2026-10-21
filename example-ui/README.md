# SMML Example UI

A small Compose Desktop (Jetpack Compose / Compose Multiplatform) mod manager that
shows off the SMML API:

- `SMML.search` across several `Source`s with `SearchRequest.Builder` (query, game version, loader, sort method/order, limit)
- `SMML.getProjectVersions` to list versions, loaders, dependencies and files
- `SMML.getSourceApiVersion` / `SMML.getVersion`
- Downloading a version file into a local `mods` folder and managing (listing/deleting) installed files

```
.\gradlew.bat :example-ui:run
```

CurseForge requires the `CFToken` environment variable to be set to a CurseForge API key.
