/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import juuxel.thisandthat.lib.*
import net.fabricmc.api.ModInitializer

object ThisAndThat : ModInitializer {
    override fun onInitialize() {
        ModConfig.init()
        ModBlocks.init()
        ModItems.init()
        ModTags.init()
        ModConfig.config.save()
    }
}
