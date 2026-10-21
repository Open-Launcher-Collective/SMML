package dev.cadindie.example

import dev.cadindie.*
import dev.cadindie.sources.SearchRequest
import dev.cadindie.sources.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Thin suspend wrappers around the SMML API. */
object Backend {
    val modsDir: File = File("mods").also { it.mkdirs() }

    private val iconCache = HashMap<String, ByteArray?>()

    suspend fun search(
        query: String,
        sources: Set<Source>,
        gameVersion: String,
        loader: Loader?,
        sortMethod: SortMethod,
        sortOrder: SortOrder,
        limit: Int,
    ): List<ResourceResult> = withContext(Dispatchers.IO) {
        val builder = SearchRequest.Builder(query)
            .sortMethod(sortMethod)
            .sortOrder(sortOrder)
            .limit(limit)
        if (gameVersion.isNotBlank()) builder.gameVersion(gameVersion.trim())
        if (loader != null) builder.loader(loader)
        // Search each source separately so one failing source (e.g. CurseForge without a key) doesn't hide the rest.
        val request = builder.build()
        val all = ArrayList<ResourceResult>()
        val errors = ArrayList<String>()
        for (source in sources) {
            try {
                SMML.search(request, source).join()?.let { all += it }
            } catch (e: Exception) {
                errors += "${source.name}: ${e.cause?.message ?: e.message}"
            }
        }
        if (all.isEmpty() && errors.isNotEmpty()) error(errors.joinToString("\n"))
        all
    }

    suspend fun versions(result: ResourceResult): List<ResourceVersion> = withContext(Dispatchers.IO) {
        SMML.getProjectVersions(result.id(), result.source()).join().toList()
    }

    suspend fun icon(url: String?): ByteArray? {
        if (url.isNullOrBlank()) return null
        return withContext(Dispatchers.IO) {
            synchronized(iconCache) { if (iconCache.containsKey(url)) return@withContext iconCache[url] }
            val bytes = try {
                URI(url).toURL().openConnection().apply {
                    setRequestProperty("User-Agent", "SMML-Example")
                    connectTimeout = 5000
                    readTimeout = 5000
                }.getInputStream().use { it.readBytes() }
            } catch (_: Exception) {
                null
            }
            synchronized(iconCache) { iconCache[url] = bytes }
            bytes
        }
    }

    suspend fun download(file: ResourceFile): File = withContext(Dispatchers.IO) {
        // Strip any path components from the remote file name
        val target = File(modsDir, File(file.fileName()).name)
        val conn = URI(file.downloadUrl()).toURL().openConnection().apply {
            setRequestProperty("User-Agent", "SMML-Example")
        }
        conn.getInputStream().use { Files.copy(it, target.toPath(), StandardCopyOption.REPLACE_EXISTING) }
        target
    }

    fun installed(): List<File> =
        modsDir.listFiles { f -> f.isFile }?.sortedBy { it.name.lowercase() } ?: emptyList()
}
