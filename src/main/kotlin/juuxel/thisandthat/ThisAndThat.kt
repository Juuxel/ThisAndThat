/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import juuxel.thisandthat.block.*
import juuxel.thisandthat.item.ModBlockItem
import juuxel.thisandthat.item.ModWallBlockItem
import juuxel.thisandthat.item.StoneRodItem
import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.ModContent
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block.Settings.copy as copySettings
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.Registry.*

object ThisAndThat : ModInitializer {
    override fun onInitialize() {
        val stoneTorchGround = registerBlock(StoneTorchBlock())
        val stoneTorchWall = registerBlock(StoneTorchBlock.Wall(copySettings(stoneTorchGround.unwrap()).dropsLike(stoneTorchGround.unwrap())))
        register(ITEM, ModWallBlockItem(stoneTorchGround, stoneTorchWall))
        register(ITEM, StoneRodItem())

        for (v in arrayOf(*BlockVariant.Wood.values(), *BlockVariant.Stone.values())) {
            val variant = v as BlockVariant

            registerBlock(PostBlock(variant))
            registerBlock(PlatformBlock(variant))
            registerBlock(StepBlock(variant))
        }

        registerBlock(ChimneyBlock())
        registerBlock(BubbleChimneyBlock())
    }

    private fun <R> register(registry: Registry<in R>, content: ModContent<R>): ModContent<R> {
        Registry.register(registry, "thisandthat:${content.name}", content.unwrap())
        return content
    }

    private fun registerBlock(content: ModBlock): ModBlock {
        register(BLOCK, content)

        if (content.registerItem)
            Registry.register(ITEM, "thisandthat:${content.name}", ModBlockItem(content))

        return content
    }
}
