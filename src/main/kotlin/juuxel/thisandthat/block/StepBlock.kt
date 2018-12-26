/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class StepBlock(variant: BlockVariant) : Block(variant.settings), ModBlock, Fluidloggable {
    override val name = "${variant.contentName}_step"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val hasDescription = true
    override val descriptionKey = "block.thisandthat.step.desc"

    init {
        defaultState = stateFactory.defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingShape(p0: BlockState?, p1: BlockView?, p2: BlockPos?): VoxelShape =
        VoxelShapes.union(postShape, platformShape)

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val state = context.world.getFluidState(context.pos)
        return this.defaultState.with(FluidProperty.FLUID, FluidProperty.Wrapper(state.fluid))
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        p0.with(FluidProperty.FLUID)
    }

    companion object {
        internal val postShape = Block.createCubeShape(6.0, 0.0, 6.0, 10.0, 8.0, 10.0)
        private val platformShape = Block.createCubeShape(0.0, 6.0, 0.0, 16.0, 8.0, 16.0)
    }
}
