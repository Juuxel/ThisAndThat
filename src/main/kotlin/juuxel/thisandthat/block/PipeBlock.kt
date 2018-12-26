/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import io.github.prospector.silk.fluid.DropletValues
import io.github.prospector.silk.fluid.FluidContainer
import io.github.prospector.silk.fluid.FluidInstance
import io.github.prospector.silk.util.ContainerInteraction
import juuxel.thisandthat.util.FluidEither
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import juuxel.watereddown.api.WDProperties
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.Tickable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.IWorld

class PipeBlock : BlockWithEntity(Block.Settings.copy(Blocks.STONE)), ModBlock, Fluidloggable {
    override val name = "pipe"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val blockEntityType: BlockEntityType<*>? = Companion.blockEntityType

    init {
        defaultState = FACING_PROPERTIES.entries.fold(stateFactory.defaultState) { state, (_, prop) ->
            state.with(prop, false)
        }.with(WDProperties.FLUID, FluidProperty.EMPTY)
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
        val fluidState = world.getFluidState(pos)
        return FACING_PROPERTIES.entries.fold(defaultState) { state, (dir, prop) ->
            state.with(prop, connectsTo(world, pos.offset(dir)))
        }.with(WDProperties.FLUID, FluidProperty.Wrapper(fluidState.fluid))
    }

    override fun getStateForNeighborUpdate(
        var1: BlockState,
        var2: Direction?,
        var3: BlockState?,
        var4: IWorld,
        var5: BlockPos?,
        var6: BlockPos
    ): BlockState {
        val fluid = var1[WDProperties.FLUID].fluid
        if (fluid != Fluids.EMPTY) {
            var4.fluidTickScheduler.schedule(var5, fluid, fluid.method_15789(var4))
        }

        return var1.with(FACING_PROPERTIES[var2], connectsTo(var4, var6))
    }

    private fun connectsTo(world: IWorld, pos: BlockPos): Boolean =
        world.getBlockEntity(pos) is FluidContainer || world.getBlockState(pos).block is PumpBlock

    override fun getRenderType(blockState_1: BlockState?) = RenderTypeBlock.MODEL
    override fun createBlockEntity(var1: BlockView?) = Entity()

    class Entity internal constructor() : FluidContainerBlockEntity(CAPACITY, blockEntityType), Tickable {
        override fun tick() {
            /*val directions = Direction.values().filter {
                world.getBlockEntity(pos.offset(it)) is FluidContainer
            }.sortedByDescending { world.getBlockState(pos.offset(it)).block is PipeBlock }

            for (d in directions) {
                (world.getBlockEntity(pos.offset(d)) as FluidContainer)
                    .tryPartialInsertFluid(d, fluid, amount / directions.size, ContainerInteraction.EXECUTE)
            }*/
        }
    }

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
        private val blockEntityType = BlockEntityType(::Entity, null)
        private const val CAPACITY = DropletValues.BUCKET
    }
}
