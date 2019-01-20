/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import io.github.cottonmc.cotton.logging.Ansi
import io.github.cottonmc.cotton.logging.ModLogger
import juuxel.thisandthat.lib.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

object ThisAndThat : ModInitializer {
    internal val logger = ModLogger(ThisAndThat::class.java, "ThisAndThat").apply {
        setPrefixFormat(Ansi.Cyan.and(Ansi.Bold))
    }

    override fun onInitialize() {
        ModBlocks.init()
        ModItems.init()
        ModMultiparts.init()
        ModTags.init()
        ModContainers.init()
    }

    object Client : ClientModInitializer {
        override fun onInitializeClient() {
            ModContainers.initClient()
        }
    }
}
