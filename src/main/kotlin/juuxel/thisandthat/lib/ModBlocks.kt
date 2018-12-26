/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.block.*
import juuxel.thisandthat.item.ModWallBlockItem
import juuxel.thisandthat.util.BlockVariant
import net.minecraft.block.Block
import net.minecraft.util.registry.Registry

object ModBlocks : ModRegistry() {
    lateinit var ryeCrop: RyeCropBlock private set

    fun init() {
        val stoneTorchGround = registerBlock(StoneTorchBlock())
        val stoneTorchWall = registerBlock(StoneTorchBlock.Wall(
            Block.Settings.copy(stoneTorchGround.unwrap()).dropsLike(stoneTorchGround.unwrap())
        ))
        register(Registry.ITEM, ModWallBlockItem(stoneTorchGround, stoneTorchWall))

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
    }
}
