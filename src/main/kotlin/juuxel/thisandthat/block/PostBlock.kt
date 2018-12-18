/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import juuxel.watereddown.api.WDProperties
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class PostBlock(variant: BlockVariant) : Block(variant.settings), ModBlock, Fluidloggable {
    override val name = "${variant.contentName}_post"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val hasDescription = true
    override val descriptionKey = "block.thisandthat.post.desc"

    init {
        defaultState = stateFactory.defaultState.with(WDProperties.FLUID, FluidProperty.EMPTY)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingShape(p0: BlockState?, p1: BlockView?, p2: BlockPos?): VoxelShape =
        PlatformBlock.postShape

    override fun hasSolidTopSurface(p0: BlockState?, p1: BlockView?, p2: BlockPos?) = true

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val state = context.world.getFluidState(context.pos)
        return this.defaultState.with(WDProperties.FLUID, FluidProperty.Wrapper(state.fluid))
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        p0.with(WDProperties.FLUID)
    }
}
