/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import com.google.gson.Gson
import io.github.cottonmc.cotton.config.annotations.ConfigFile
import io.github.cottonmc.repackage.blue.endless.jankson.Comment
import juuxel.thisandthat.ThisAndThat
import juuxel.thisandthat.util.CloudColorMode
import net.fabricmc.loader.FabricLoader
import java.io.File

@ConfigFile(name = "ThisAndThat")
class _ModConfig {
    @JvmField var items = Items()
    @JvmField var modules = Modules()
    @JvmField var tweaks = Tweaks()

    class Items {
        @JvmField var enderFeathers = true
    }

    class Modules {
        @Comment("If true, enables multiparts. Default: true")
        @JvmField var multiparts = true
    }

    class Tweaks {
        @Comment("The sky hue, in the range 0.0-1.0. Default: 0.62222224")
        @JvmField var skyHue = 0.62222224f

        @Comment("The sky saturation, in the range 0.0-1.0. Default: 0.5")
        @JvmField var skySaturation = 0.5f

        @Comment("The sky brightness, in the range 0.0-1.0. Default: 1")
        @JvmField var skyBrightness = 1f

        @Comment("If true, modifies the sky color. Default: false")
        @JvmField var changeSkyColor = false

        @Comment("The red cloud color component modifier in a 0.0-1.0 RGB color. Default: 0")
        @JvmField var cloudRedModifier = 0.0

        @Comment("The green cloud color component modifier in a 0.0-1.0 RGB color. Default: 0")
        @JvmField var cloudGreenModifier = 0.0

        @Comment("The blue cloud color component modifier in a 0.0-1.0 RGB color. Default: 0")
        @JvmField var cloudBlueModifier = 0.0

        @Comment("The cloud color mode. Values: [Modify, Replace, Vanilla], default: Vanilla")
        @JvmField var cloudColorMode = CloudColorMode.Vanilla
    }

    companion object {
        @JvmStatic
        val instance by lazy {
            ThisAndThat.logger.info("Loading config (GSON)")
//            ConfigManager.loadConfig(ModConfig::class.java)
            Gson().fromJson(File(FabricLoader.INSTANCE.configDirectory, "ThisAndThat.conf").reader(), _ModConfig::class.java)
        }
    }
}
