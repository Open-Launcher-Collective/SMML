package dev.cadindie.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.loadImageBitmap
import dev.cadindie.*
import dev.cadindie.sources.Source
import kotlinx.coroutines.launch
import java.io.File

private val searchableSources = listOf(Source.MODRINTH, Source.CURSEFORGE, Source.HANGAR, Source.SPIGET)

@Composable
fun App() = MaterialTheme(colorScheme = darkColorScheme()) {
    Surface(Modifier.fillMaxSize()) {
        var tab by remember { mutableStateOf(0) }
        var installed by remember { mutableStateOf(Backend.installed()) }
        Column {
            TabRow(selectedTabIndex = tab) {
                Tab(tab == 0, { tab = 0 }, text = { Text("Browse") })
                Tab(tab == 1, { tab = 1 }, text = { Text("Installed (${installed.size})") })
            }
            Box(Modifier.weight(1f)) {
                when (tab) {
                    0 -> BrowseScreen(onInstalled = { installed = Backend.installed() })
                    else -> InstalledScreen(installed, onChange = { installed = Backend.installed() })
                }
            }
            Text(
                "SMML ${SMML.getVersion()} | Modrinth API ${SMML.getSourceApiVersion(Source.MODRINTH)} | mods folder: ${Backend.modsDir.absolutePath}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> Dropdown(label: String, value: T, options: List<T>, text: (T) -> String, onSelect: (T) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded, { expanded = it }, modifier) {
        OutlinedTextField(
            text(value), {}, readOnly = true, singleLine = true, label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            options.forEach { o -> DropdownMenuItem({ Text(text(o)) }, { onSelect(o); expanded = false }) }
        }
    }
}

@Composable
private fun BrowseScreen(onInstalled: () -> Unit) {
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("Sodium") }
    var gameVersion by remember { mutableStateOf("1.20.1") }
    var loader by remember { mutableStateOf<Loader?>(null) }
    var sortMethod by remember { mutableStateOf(SortMethod.DOWNLOADS) }
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) }
    var limit by remember { mutableStateOf("10") }
    var sources by remember { mutableStateOf(setOf(Source.MODRINTH)) }

    var results by remember { mutableStateOf<List<ResourceResult>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var selected by remember { mutableStateOf<ResourceResult?>(null) }

    var page by remember { mutableStateOf(1) }

    fun search(newPage: Int = 1) {
        loading = true; error = null; selected = null
        scope.launch {
            try {
                results = Backend.search(
                    query, sources, gameVersion, loader, sortMethod, sortOrder, limit.toIntOrNull()?.coerceIn(1, 50) ?: 10, newPage,
                )
                page = newPage
            } catch (e: Exception) {
                results = emptyList(); error = e.message ?: e.toString()
            }
            loading = false
        }
    }

    Row(Modifier.fillMaxSize().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(query, { query = it }, label = { Text("Search") }, singleLine = true, modifier = Modifier.weight(1f))
                Button({ search() }, enabled = !loading && query.isNotBlank() && sources.isNotEmpty()) { Text("Search") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(gameVersion, { gameVersion = it }, label = { Text("MC version") }, singleLine = true, modifier = Modifier.weight(1f))
                Dropdown("Loader", loader, listOf<Loader?>(null) + Loader.values().filter { it != Loader.UNKNOWN }, { it?.name ?: "Any" }, { loader = it }, Modifier.weight(1f))
                OutlinedTextField(limit, { limit = it.filter(Char::isDigit) }, label = { Text("Limit") }, singleLine = true, modifier = Modifier.width(80.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Dropdown("Sort by", sortMethod, SortMethod.values().toList(), { it.name }, { sortMethod = it }, Modifier.weight(1f))
                Dropdown("Order", sortOrder, SortOrder.values().toList(), { it.name }, { sortOrder = it }, Modifier.weight(1f))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                searchableSources.forEach { s ->
                    Checkbox(s in sources, { sources = if (it) sources + s else sources - s })
                    Text(s.name, Modifier.padding(end = 8.dp))
                }
            }
            if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                TextButton({ search(page - 1) }, enabled = !loading && page > 1) { Text("Previous") }
                Text("Page $page")
                TextButton({ search(page + 1) }, enabled = !loading && results.isNotEmpty()) { Text("Next") }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(results) { r -> ResultCard(r, r === selected) { selected = r } }
            }
        }
        Box(Modifier.weight(1f)) {
            selected?.let { DetailsPane(it, onInstalled) } ?: Text("Select a result to view its versions.")
        }
    }
}

@Composable
private fun IconImage(url: String?, size: Int = 48) {
    var bmp by remember(url) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(url) {
        bmp = Backend.icon(url)?.let { b -> runCatching { loadImageBitmap(b.inputStream()) }.getOrNull() }
    }
    val mod = Modifier.size(size.dp).clip(RoundedCornerShape(8.dp))
    bmp?.let { Image(it, null, mod) } ?: Box(mod.then(Modifier.padding(0.dp))) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceVariant) {}
    }
}

@Composable
private fun ResultCard(r: ResourceResult, selected: Boolean, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IconImage(r.iconUrl())
            Column {
                Text("${r.name()} (${r.source()})", style = MaterialTheme.typography.titleSmall)
                Text("by ${r.author()} | ${r.downloads()} downloads | ${r.resourceType()}", style = MaterialTheme.typography.labelSmall)
                Text(r.description() ?: "", maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DetailsPane(r: ResourceResult, onInstalled: () -> Unit) {
    val scope = rememberCoroutineScope()
    var versions by remember(r) { mutableStateOf<List<ResourceVersion>>(emptyList()) }
    var loading by remember(r) { mutableStateOf(true) }
    var status by remember(r) { mutableStateOf<String?>(null) }

    LaunchedEffect(r) {
        loading = true
        try {
            versions = Backend.versions(r)
        } catch (e: Exception) {
            status = "Failed to load versions: ${e.cause?.message ?: e.message}"
        }
        loading = false
    }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            IconImage(r.iconUrl(), 64)
            Column {
                Text(r.name(), style = MaterialTheme.typography.titleLarge)
                Text("${r.source()} | ${r.author()} | loaders: ${r.loaders()?.joinToString() ?: "?"}", style = MaterialTheme.typography.labelSmall)
            }
        }
        Text(r.description() ?: "", style = MaterialTheme.typography.bodySmall)
        status?.let { Text(it, color = MaterialTheme.colorScheme.tertiary) }
        if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(versions) { v ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(8.dp)) {
                        Text(v.versionName() ?: v.version(), style = MaterialTheme.typography.titleSmall)
                        Text(
                            "v${v.version()} | MC ${v.mcVersions()?.joinToString() ?: "?"} | ${v.loaders()?.joinToString() ?: "?"} | ${v.dependencies()?.size ?: 0} dependencies",
                            style = MaterialTheme.typography.labelSmall,
                        )
                        v.files()?.forEach { f ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("${f.fileName()} (${f.fileSize() / 1024} KiB)", Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                TextButton({
                                    status = "Downloading ${f.fileName()}..."
                                    scope.launch {
                                        status = try {
                                            val file = Backend.download(f); onInstalled(); "Installed ${file.name}"
                                        } catch (e: Exception) {
                                            "Download failed: ${e.message}"
                                        }
                                    }
                                }) { Text("Install") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InstalledScreen(files: List<File>, onChange: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Installed files", Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            TextButton(onChange) { Text("Refresh") }
        }
        if (files.isEmpty()) Text("Nothing installed yet. Use the Browse tab to install a mod.")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(files) { f ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(f.name)
                            Text("${f.length() / 1024} KiB", style = MaterialTheme.typography.labelSmall)
                        }
                        TextButton({ f.delete(); onChange() }) { Text("Delete") }
                    }
                }
            }
        }
    }
}
