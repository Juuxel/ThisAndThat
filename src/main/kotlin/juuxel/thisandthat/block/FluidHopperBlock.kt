/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import io.github.prospector.silk.fluid.FluidContainer
import juuxel.thisandthat.util.ModBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import java.util.*

class FluidHopperBlock : Block(Settings.copy(Blocks.HOPPER)), ModBlock {
    override val name = "fluid_hopper"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)

    // TODO wtf
    override fun scheduledTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (world.getReceivedRedstonePower(pos) > 0) return
        val above = world.getBlockEntity(pos.up()) as? FluidContainer ?: return
        val below = world.getBlockEntity(pos.down()) as? FluidContainer ?: return
        val fluid = above.getFluids(Direction.DOWN).firstOrNull()?.fluid ?: return

        if (above.canExtractFluid(Direction.DOWN, fluid, 1) &&
                below.canInsertFluid(Direction.UP, fluid, 1)) {
            above.extractFluid(Direction.DOWN, fluid, 1)
            below.insertFluid(Direction.UP, fluid, 1)
        }
    }
}
