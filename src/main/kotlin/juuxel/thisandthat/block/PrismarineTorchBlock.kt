/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateFactory

class PrismarineTorchBlock : TorchBlock(settings), ModBlock, Fluidloggable {
    override val name = "prismarine_torch"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val registerItem = false
    override val hasDescription = true

    init {
        defaultState = defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        return Fluidloggable.onGetPlacementState(context, super.getPlacementState(context))
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(p0)
        Fluidloggable.onAppendProperties(p0)
    }

    class Wall(settings: Settings) : WallTorchBlock(settings), ModBlock, Fluidloggable {
        override val name = "wall_prismarine_torch"
        override val itemSettings = null
        override val registerItem = false

        init {
            defaultState = defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
        }

        override fun getPlacementState(context: ItemPlacementContext): BlockState? {
            val state = context.world.getFluidState(context.blockPos)
            return super.getPlacementState(context)?.with(FluidProperty.FLUID, FluidProperty.Wrapper(state.fluid))
        }

        override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
            super.appendProperties(p0)
            p0.with(FluidProperty.FLUID)
        }
    }

    companion object {
        internal val settings = FabricBlockSettings
            .of(Material.Builder(MaterialColor.STONE).build())
            .collidable(false)
            .breakByHand(true)
            .strength(0f, 0f)
            .lightLevel(15)
            .sounds(BlockSoundGroup.STONE)
            .build()
    }
}
