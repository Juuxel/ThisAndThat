/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.lib.ModTags
import juuxel.thisandthat.util.ModBlock
import juuxel.watereddown.api.FluidProperty
import juuxel.watereddown.api.Fluidloggable
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderLayer
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.VerticalEntityPosition
import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.ViewableWorld
import net.minecraft.world.World
import java.util.*

class WetFireBlock : Block(FabricBlockSettings.copy(Blocks.FIRE).ticksRandomly().build()), ModBlock, Fluidloggable {
    override val name = "wet_fire"
    override val itemSettings = null
    override val registerItem = false

    init {
        defaultState = stateFactory.defaultState.with(FluidProperty.FLUID, FluidProperty.EMPTY)
    }

    override fun canPlaceAt(state: BlockState?, world: ViewableWorld, pos: BlockPos) =
        world.getBlockState(pos.down()).hasSolidTopSurface(world, pos.down()) &&
            world.getBlockState(pos).let { it.isAir || it.material.isLiquid }

    override fun onRandomTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        extinguishIfCantBurn(world, pos)
    }

    override fun neighborUpdate(
        state: BlockState?,
        world: World,
        pos: BlockPos,
        block_1: Block,
        blockPos_2: BlockPos
    ) {
        if (!canPlaceAt(state, world, pos))
            world.clearBlockState(pos)
    }

    private fun extinguishIfCantBurn(world: World, pos: BlockPos) {
        val wrongBlock = !world.getBlockState(pos.down()).block.matches(ModTags.keepsWetFireBurning)

        if (!canPlaceAt(null, world, pos) || wrongBlock ||
            !world.getFluidState(pos).fluid.matches(ModTags.canContainWetFire)) {
            world.clearBlockState(pos)
        }
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        return Fluidloggable.onGetPlacementState(context, defaultState)
    }

    override fun appendProperties(p0: StateFactory.Builder<Block, BlockState>) {
        Fluidloggable.onAppendProperties(p0)
    }

    override fun hasRandomTicks(state: BlockState?) = true

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        Blocks.FIRE.randomDisplayTick(state, world, pos, random)
    }

    override fun getRenderLayer() = BlockRenderLayer.CUTOUT
    override fun getOutlineShape(state: BlockState?, view: BlockView?, pos: BlockPos?, vep: VerticalEntityPosition?) =
        VoxelShapes.empty()

    override fun onEntityCollision(
        state: BlockState,
        world: World,
        pos: BlockPos,
        entity: Entity
    ) {
        if (!entity.isTouchingWater) {
            entity.setOnFireFor(8)
        }

        if (!entity.isFireImmune)
            entity.damage(DamageSource.IN_FIRE, 1f)
    }

    override fun getTickRate(world: ViewableWorld?) =
        Blocks.FIRE.getTickRate(world)

    override fun getTranslationKey() = Blocks.FIRE.translationKey
}
