/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import io.github.prospector.silk.fluid.DropletValues
import io.github.prospector.silk.fluid.FluidContainer
import io.github.prospector.silk.util.ContainerInteraction
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
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)

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
        if ((world.getBlockEntity(pos.down()) !is FluidContainer && world.getBlockState(pos.down()).block !is FluidBlock) ||
            (world.getBlockEntity(pos.up()) !is FluidContainer)) {
            return false
        }

        val above = world.getBlockEntity(pos.up()) as FluidContainer

        when {
            world.getBlockEntity(pos.down()) is FluidContainer -> {
                val below = world.getBlockEntity(pos.down()) as FluidContainer
                val fluid = below.getFluids(Direction.UP).first().fluid
                val amount = below.tryPartialExtractFluid(Direction.UP, fluid, DropletValues.BUCKET,
                    ContainerInteraction.SIMULATE)

                if (amount > 0 && above.canInsertFluid(Direction.DOWN, fluid, amount)) {
                    above.insertFluid(Direction.DOWN, fluid, amount)
                    below.tryPartialExtractFluid(Direction.UP, fluid, DropletValues.BUCKET, ContainerInteraction.EXECUTE)
                }
            }

            world.getBlockState(pos.down()).block is FluidBlock -> {
                val belowState = world.getBlockState(pos.down())

                if (belowState.fluidState.isStill && above.canInsertFluid(
                        Direction.DOWN,
                        belowState.fluidState.fluid,
                        DropletValues.BUCKET
                    )
                ) {
                    world.setBlockState(pos.down(), Blocks.AIR.defaultState)
                    above.insertFluid(Direction.DOWN, belowState.fluidState.fluid, DropletValues.BUCKET)
                }
            }
        }

        return true
    }
}
