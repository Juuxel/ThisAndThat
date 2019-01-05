/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import a2u.tn.utils.json.JsonParser
import a2u.tn.utils.json.JsonSerializer
import java.nio.file.Files
import java.nio.file.Path
import org.apache.logging.log4j.LogManager

/**
 * A JSON5-based config type.
 */
class Config(private val file: Path) {
    @PublishedApi
    internal lateinit var map: MutableMap<String, Any>

    private val logger = LogManager.getLogger()

    fun load() {
        if (Files.notExists(file)) {
            logger.info("[ThisAndThat] Creating config file $file")
            map = HashMap()
            save()
        } else {
            map = JsonParser.parse(
                Files.readAllLines(file).joinToString(separator = "\n")
            )
        }
    }

    fun save() {
        Files.write(file, JsonSerializer.toJson(map, JsonSerializer.Mode.JSON5).lines())
    }

    /**
     * Reads a value based on the [location].
     *
     * [defaultValue] is saved in the config if the value is not found.
     *
     * ## Example
     * `location` = `items.enderFeather`
     *
     * In the file:
     * ```
     * {
     *   items: {
     *     enderFeather: true
     *   }
     * }
     * ```
     */
    @Suppress("UNCHECKED_CAST")
    inline operator fun <reified T : Any> get(location: String, defaultValue: T): T {
        val parts = location.split('.')
        var targetMap: MutableMap<String, Any> = map

        for (part in parts.dropLast(1)) {
            targetMap = map.getOrPut(part) { HashMap<String, Any>() } as? MutableMap<String, Any> ?:
                throw IllegalStateException("$part in $location is not a map!")
        }

        return targetMap.getOrPut(parts.last()) { defaultValue } as T
    }
}
