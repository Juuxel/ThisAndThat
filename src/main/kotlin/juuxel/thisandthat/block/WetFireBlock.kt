/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.lib.ModTags
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderLayer
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.ViewableWorld
import net.minecraft.world.World
import java.util.*

class WetFireBlock : Block(Settings.copy(Blocks.FIRE)), ModBlock, Fluidloggable {
    override val name = "wet_fire"
    override val itemSettings = null
    override val registerItem = false

    init {
        defaultState = stateFactory.defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    override fun getBoundingShape(state: BlockState?, view: BlockView?, pos: BlockPos?) =
        VoxelShapes.empty()

    override fun canPlaceAt(state: BlockState?, world: ViewableWorld, pos: BlockPos) =
        world.getBlockState(pos.down()).hasSolidTopSurface(world, pos.down()) &&
            world.getBlockState(pos).let { it.isAir || it.material.isLiquid }

    override fun scheduledTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        extinguishIfCantBurn(world, pos)
    }

    private fun extinguishIfCantBurn(world: World, pos: BlockPos) {
        val wrongBlock = !world.getBlockState(pos.down()).block.matches(ModTags.keepsWetFireBurning)

        if (!canPlaceAt(null, world, pos) || wrongBlock ||
            !world.getFluidState(pos).fluid.matches(ModTags.canContainWetFire)) {
            world.clearBlockState(pos)
        }
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val state = context.world.getFluidState(context.pos)
        return this.defaultState.with(FluidProperty.FLUID, FluidProperty.Wrapper(state.fluid))
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        p0.with(FluidProperty.FLUID)
    }

    override fun hasRandomTicks(state: BlockState?) = true

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        Blocks.FIRE.randomDisplayTick(state, world, pos, random)
    }

    override fun getRenderLayer() = BlockRenderLayer.CUTOUT
    override fun canCollideWith() = false

    override fun getTickRate(world: ViewableWorld?) =
        Blocks.FIRE.getTickRate(world)

    override fun getTranslationKey() = Blocks.FIRE.translationKey
}
