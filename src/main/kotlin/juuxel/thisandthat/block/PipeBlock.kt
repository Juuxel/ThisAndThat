/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.api.FluidContainer
import juuxel.thisandthat.api.FluidStack
import juuxel.thisandthat.api.FluidStack2
import juuxel.thisandthat.api.TransferAmount
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
import net.minecraft.nbt.CompoundTag
import net.minecraft.state.StateFactory
import net.minecraft.util.Identifier
import net.minecraft.util.Tickable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.IWorld
import net.minecraft.world.World
import kotlin.math.min

class PipeBlock : BlockWithEntity(Block.Settings.copy(Blocks.STONE)), ModBlock, Fluidloggable, FluidContainer {
    override val name = "pipe"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.REDSTONE)
    override val blockEntityType = BLOCK_ENTITY_TYPE

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
        return FACING_PROPERTIES.entries.fold(defaultState) { state, (dir, property) ->
            state.with(property, connectsTo(world.getBlockState(pos.offset(dir))))
        }.with(WDProperties.FLUID, FluidProperty.Wrapper(fluidState.fluid))
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

    override val insertableAmount = TransferAmount.max(FluidStack.AMOUNT_BUCKET)
    override fun getCapacity(world: World, pos: BlockPos, state: BlockState) = CAPACITY
    override fun getContents(world: World, pos: BlockPos, state: BlockState) =
        (world.getBlockEntity(pos) as Entity).getContents()

    override fun canInsert(
        world: World,
        pos: BlockPos,
        state: BlockState,
        side: Direction,
        stack: FluidStack
    ) = (world.getBlockEntity(pos) as Entity).canInsert(stack)

    override fun canExtract(
        world: World,
        pos: BlockPos,
        state: BlockState,
        side: Direction,
        amount: TransferAmount
    ) = (world.getBlockEntity(pos) as Entity).canExtract(amount)

    override fun extract(
        world: World,
        pos: BlockPos,
        state: BlockState,
        side: Direction,
        amount: TransferAmount,
        simulated: Boolean
    ): FluidStack = (world.getBlockEntity(pos) as Entity).extract(amount, simulated)

    override fun insert(world: World, pos: BlockPos, state: BlockState, side: Direction, stack: FluidStack) =
        (world.getBlockEntity(pos) as Entity).insert(stack)
    override fun createBlockEntity(var1: BlockView?) = Entity()
    override fun getRenderType(var1: BlockState) = RenderTypeBlock.MODEL

    companion object {
        val BLOCK_ENTITY_TYPE = BlockEntityType(::Entity, null)

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
        private const val CAPACITY = FluidStack.AMOUNT_BUCKET
    }

    class Entity internal constructor() : BlockEntity(BLOCK_ENTITY_TYPE), Tickable {
        private var stack: FluidStack2 = FluidStack2.Empty
        private var fluid: Fluid = Fluids.EMPTY
        private var fluidAmount: Int = 0

        fun getContents() = setOf(FluidStack(fluid, fluidAmount))

        fun canInsert(stack: FluidStack) =
            fluidAmount <= CAPACITY /*- fluidAmount*/ && // TODO Uncomment
                fluid in arrayOf(Fluids.EMPTY, stack.fluid)

        fun canExtract(amount: TransferAmount) =
            amount.min <= fluidAmount && amount.max <= FluidStack.AMOUNT_BUCKET

        fun extract(
            amount: TransferAmount,
            simulated: Boolean
        ): FluidStack {
            if (!canExtract(amount)) return FluidStack.EMPTY

            val stackAmount = min(amount.max, fluidAmount)
            if (!simulated) {
                fluidAmount -= stackAmount
                if (fluidAmount == 0)
                    fluid = Fluids.EMPTY
            }

            return FluidStack(fluid, stackAmount)
        }

        fun insert(stack: FluidStack): Boolean {
            if (!canInsert(stack)) return false

            if (stack.fluid != Fluids.EMPTY)
                fluid = stack.fluid
            fluidAmount += stack.amount
            fluidAmount = min(CAPACITY, fluidAmount) // TODO Remove, this is debug

            return true
        }

        override fun tick() {
            if (fluid == Fluids.EMPTY) return

            Direction.values().filter {
                val block = world.getBlockState(pos.offset(it)).block as? FluidContainer ?: return@filter false
                block.canInsert(world, pos.offset(it), world.getBlockState(pos.offset(it)),
                        it, extract(TransferAmount.max(fluidAmount), true))
            }.sortedBy { world.getBlockState(pos.offset(it)).block !is PipeBlock }.forEach {
                val block = world.getBlockState(pos.offset(it)).block as FluidContainer
                block.insert(world, pos.offset(it), world.getBlockState(pos.offset(it)),
                    it, extract(TransferAmount.max(fluidAmount), false)
                )
            }
        }

        override fun toTag(var1: CompoundTag) = var1.apply {
            putString("fluid", Registry.FLUID.getId(fluid).toString())
            putInt("amount", fluidAmount)
        }

        override fun fromTag(tag: CompoundTag) {
            super.fromTag(tag)

            if (tag.containsKey("fluid"))
                fluid = Registry.FLUID[Identifier(tag.getString("fluid"))]

            if (tag.containsKey("amount"))
                fluidAmount = tag.getInt("amount")
        }
    }
}
