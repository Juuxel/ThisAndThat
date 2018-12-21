/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.api.FluidContainer
import juuxel.thisandthat.api.FluidStack
import juuxel.thisandthat.util.ModBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class PumpBlock : Block(Block.Settings.copy(Blocks.STONE)), ModBlock {
    override val name = "pump"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.REDSTONE)

    override fun activate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        var4: PlayerEntity?,
        var5: Hand?,
        var6: Direction?,
        var7: Float,
        var8: Float,
        var9: Float
    ): Boolean {
        if ((world.getBlockState(pos.down()).block !is FluidContainer && world.getBlockState(pos.down()).block !is FluidBlock) ||
            world.getBlockState(pos.up()).block !is FluidContainer
        ) {
            return false
        }

        val abovePos = pos.up()
        val aboveState = world.getBlockState(abovePos)
        val above = aboveState.block as FluidContainer

        when (world.getBlockState(pos.down()).block) {
            is FluidContainer -> {
                val belowState = world.getBlockState(pos.down())
                val below = belowState.block as FluidContainer
                val stack = below.extract(world, pos.down(), belowState, Direction.UP, FluidStack.AMOUNT_BUCKET, simulated = true)

                if (stack.amount > 0) {
                    above.insert(world, abovePos, aboveState, Direction.DOWN, stack)
                    below.extract(world, pos.down(), belowState, Direction.UP, FluidStack.AMOUNT_BUCKET, simulated = false)
                }
            }

            is FluidBlock -> {
                val belowState = world.getBlockState(pos.down())

                if (belowState.fluidState.isStill) {
                    val stack = FluidStack(belowState.fluidState.fluid, FluidStack.AMOUNT_BUCKET)
                    world.setBlockState(pos.down(), Blocks.AIR.defaultState)
                    above.insert(world, abovePos, aboveState, Direction.DOWN, stack)
                }
            }
        }

        return true
    }
}
