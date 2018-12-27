/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.block.*
import juuxel.thisandthat.item.ModWallBlockItem
import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModBlock
import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object ModBlocks : ModRegistry() {
    lateinit var ryeCrop: RyeCropBlock private set
    lateinit var wetFire: WetFireBlock private set

    fun init() {
        registerWallBlock(::StoneTorchBlock, { StoneTorchBlock.Wall(it) })
        registerWallBlock(::PrismarineTorchBlock, { PrismarineTorchBlock.Wall(it) })

        for (v in arrayOf(*BlockVariant.Wood.values(), *BlockVariant.Stone.values())) {
            val variant = v as BlockVariant

            registerBlock(PostBlock(variant))
            registerBlock(PlatformBlock(variant))
            registerBlock(StepBlock(variant))
        }

        registerBlock(ChimneyBlock())
        registerBlock(BubbleChimneyBlock())
        ryeCrop = registerBlock(RyeCropBlock())
        registerBlock(TankBlock())
        wetFire = registerBlock(WetFireBlock())
    }

    private fun registerWallBlock(ground: () -> ModBlock, wall: (Block.Settings) -> ModBlock) {
        val groundInstance = registerBlock(ground())
        val wallInstance = registerBlock(wall(
            Block.Settings.copy(groundInstance.unwrap()).dropsLike(groundInstance.unwrap())
        ))

        register(Registry.ITEM, ModWallBlockItem(groundInstance, wallInstance))
    }
}
