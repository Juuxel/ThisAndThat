/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.api.FluidContainer
import juuxel.thisandthat.api.FluidStack
import juuxel.thisandthat.api.TransferAmount
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.WDProperties.FLUID
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.GlassBlock
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class TankBlock : GlassBlock(Settings.copy(Blocks.GLASS)), ModBlock, FluidContainer {
    override val name = "tank"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val insertableAmount = TransferAmount.exact(FluidStack.AMOUNT_BUCKET)

    init {
        defaultState = stateFactory.defaultState.with(FLUID, FluidProperty.EMPTY)
    }

    override fun appendProperties(var1: StateFactory.Builder<Block, BlockState>) {
        var1.with(FLUID)
    }

    override fun canInsert(world: World, pos: BlockPos, state: BlockState, side: Direction, stack: FluidStack) =
        state[FLUID].fluid == Fluids.EMPTY && stack.amount == FluidStack.AMOUNT_BUCKET

    override fun canExtract(
        world: World,
        pos: BlockPos,
        state: BlockState,
        side: Direction,
        amount: TransferAmount
    ): Boolean =
        state[FLUID].fluid != Fluids.EMPTY && !(amount.min > FluidStack.AMOUNT_BUCKET || amount.max < FluidStack.AMOUNT_BUCKET)

    override fun extract(world: World, pos: BlockPos, state: BlockState, side: Direction, amount: TransferAmount, simulated: Boolean): FluidStack {
        if (!canExtract(world, pos, state, side, amount))
            return FluidStack.EMPTY

        val fluid = state[FLUID].fluid
        if (!simulated) world.setBlockState(pos, state.with(FLUID, FluidProperty.EMPTY))
        return FluidStack(fluid, FluidStack.AMOUNT_BUCKET)
    }

    override fun insert(world: World, pos: BlockPos, state: BlockState, side: Direction, stack: FluidStack): Boolean =
        if (canInsert(world, pos, state, side, stack)) {
            world.setBlockState(pos, state.with(FLUID, FluidProperty.Wrapper(stack.fluid)))
            true
        } else false

    override fun getCapacity(world: World, pos: BlockPos, state: BlockState) =
        FluidStack.AMOUNT_BUCKET

    override fun getContents(world: World, pos: BlockPos, state: BlockState) =
        setOf(
            if (state[FLUID].fluid == Fluids.EMPTY) FluidStack.EMPTY
            else FluidStack(state[FLUID].fluid, FluidStack.AMOUNT_BUCKET)
        )
}
