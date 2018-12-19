/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.api.FluidContainer
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import juuxel.watereddown.api.WDProperties
import net.minecraft.block.*
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.IWorld

class PipeBlock : Block(Block.Settings.copy(Blocks.STONE)), ModBlock, Fluidloggable {
    override val name = "pipe"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.REDSTONE)

    init {
        defaultState = stateFactory.defaultState
            .with(NORTH, false)
            .with(EAST, false)
            .with(SOUTH, false)
            .with(WEST, false)
            .with(UP, false)
            .with(DOWN, false)
            .with(WDProperties.FLUID, FluidProperty.EMPTY)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingShape(p0: BlockState?, p1: BlockView?, p2: BlockPos?): VoxelShape =
        PlatformBlock.postShape

    override fun appendProperties(var1: StateFactory.Builder<Block, BlockState>) {
        var1.with(NORTH, EAST, SOUTH, WEST, UP, DOWN, WDProperties.FLUID)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val world = context.world
        val pos = context.pos
        val state = world.getFluidState(pos)
        return this.defaultState.with(WDProperties.FLUID, FluidProperty.Wrapper(state.fluid))
            .with(NORTH, connectsTo(world.getBlockState(pos.north())))
            .with(EAST, connectsTo(world.getBlockState(pos.east())))
            .with(SOUTH, connectsTo(world.getBlockState(pos.south())))
            .with(WEST, connectsTo(world.getBlockState(pos.west())))
            .with(UP, connectsTo(world.getBlockState(pos.up())))
            .with(DOWN, connectsTo(world.getBlockState(pos.down())))
    }

    override fun getStateForNeighborUpdate(
        var1: BlockState,
        var2: Direction?,
        var3: BlockState?,
        var4: IWorld,
        var5: BlockPos?,
        var6: BlockPos?
    ): BlockState {
        val fluid = var1[WDProperties.FLUID].fluid
        if (fluid != Fluids.EMPTY) {
            var4.fluidTickScheduler.schedule(var5, fluid, fluid.method_15789(var4))
        }

        return var1.with(FACING_PROPERTIES[var2], connectsTo(var4.getBlockState(var6)))
    }

    private fun connectsTo(state: BlockState): Boolean =
        state.block is PipeBlock || state.block is FluidContainer || state.block is PumpBlock

    companion object {
        private val NORTH = ConnectedPlantBlock.NORTH
        private val EAST = ConnectedPlantBlock.EAST
        private val SOUTH = ConnectedPlantBlock.SOUTH
        private val WEST = ConnectedPlantBlock.WEST
        private val UP = ConnectedPlantBlock.UP
        private val DOWN = ConnectedPlantBlock.DOWN
        private val FACING_PROPERTIES = mapOf(
            Direction.NORTH to NORTH,
            Direction.EAST to EAST,
            Direction.SOUTH to SOUTH,
            Direction.WEST to WEST,
            Direction.UP to UP,
            Direction.DOWN to DOWN
        )
    }
}
