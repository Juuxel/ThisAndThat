/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.minecraft.block.*
import net.minecraft.fluid.BaseFluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory

class TankBlock : GlassBlock(Settings.copy(Blocks.GLASS)), ModBlock, Fluidloggable {
    override val name = "tank"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val hasDescription = true

    init {
        defaultState = stateFactory.defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        builder.with(FluidProperty.FLUID)
    }

    override fun getPlacementState(
        context: ItemPlacementContext
    ): BlockState? {
        val state = context.world.getFluidState(context.pos)
        return super.getPlacementState(context)?.apply {
            (state.fluid as? BaseFluid)?.let {
                with(FluidProperty.FLUID, FluidProperty.Wrapper(it.still))
            }
        }
    }
}
