/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import io.github.prospector.silk.fluid.DropletValues
import juuxel.thisandthat.util.ModBlock
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.BlockRenderLayer
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class TankBlock : BlockWithEntity(Settings.copy(Blocks.GLASS)), ModBlock {
    override val name = "tank"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val blockEntityType: BlockEntityType<*>? = Companion.blockEntityType

    // Copied from GlassBlock
    override fun method_9579(var1: BlockState, var2: BlockView, var3: BlockPos) = true
    override fun getRenderLayer() = BlockRenderLayer.CUTOUT
    override fun isSimpleFullBlock(var1: BlockState, var2: BlockView, var3: BlockPos) = false

    override fun getRenderType(blockState_1: BlockState?) = RenderTypeBlock.MODEL
    override fun createBlockEntity(var1: BlockView?) = Entity()

    class Entity internal constructor() : FluidContainerBlockEntity(CAPACITY, blockEntityType)

    companion object {
        private val blockEntityType = BlockEntityType(::Entity, null)
        private const val CAPACITY = DropletValues.BUCKET
    }
}
