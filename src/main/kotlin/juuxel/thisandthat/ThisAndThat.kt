/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import juuxel.thisandthat.block.*
import juuxel.thisandthat.item.ModBlockItem
import juuxel.thisandthat.item.ModWallBlockItem
import juuxel.thisandthat.item.StoneRodItem
import juuxel.thisandthat.lib.ModBlocks
import juuxel.thisandthat.lib.ModItems
import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.ModContent
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block.Settings.copy as copySettings
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.Registry.*

object ThisAndThat : ModInitializer {
    override fun onInitialize() {
        ModBlocks.init()
        ModItems.init()
    }
}
