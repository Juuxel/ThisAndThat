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
import net.minecraft.entity.VerticalEntityPosition
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
    override fun getOutlineShape(state: BlockState?, view: BlockView?, pos: BlockPos?, vep: VerticalEntityPosition?): VoxelShape =
        VoxelShapes.union(postShape, platformShape)

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        return Fluidloggable.onGetPlacementState(context, defaultState)
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        Fluidloggable.onAppendProperties(p0)
    }

    companion object {
        internal val postShape = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 8.0, 10.0)
        private val platformShape = Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 8.0, 16.0)
    }
}
