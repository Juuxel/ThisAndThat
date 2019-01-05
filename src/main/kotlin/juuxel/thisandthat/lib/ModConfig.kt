/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.util.Config
import net.fabricmc.loader.FabricLoader
import java.nio.file.Paths

object ModConfig {
    val config = Config(
        Paths.get(
            FabricLoader.INSTANCE.configDirectory.absolutePath,
            "thisandthat.json5"
        )
    ).apply { load() }

    val enderFeathers = config["items.enderFeathers", false] // false for now
    val multiparts = config["modules.multipart", true]

    fun init() {}
}
