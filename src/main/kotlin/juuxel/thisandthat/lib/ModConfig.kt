/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import io.github.cottonmc.cotton.config.ConfigManager
import io.github.cottonmc.cotton.config.annotations.ConfigFile
import io.github.cottonmc.repackage.blue.endless.jankson.Comment

@ConfigFile(name = "ThisAndThat")
class ModConfig {
    @JvmField var items = Items()
    @JvmField var modules = Modules()

    class Items {
        @JvmField var enderFeathers = true
    }

    class Modules {
        @Comment("If true, enables multiparts.")
        @JvmField var multiparts = true
    }

    companion object {
        @JvmStatic
        val instance by lazy {
            ConfigManager.loadConfig(ModConfig::class.java)
        }
    }
}
