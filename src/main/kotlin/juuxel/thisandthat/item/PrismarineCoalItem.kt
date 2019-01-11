/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.lib.ModBlocks
import net.minecraft.advancement.criterion.Criterions
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult

class PrismarineCoalItem : ModItem("prismarine_coal", Settings().itemGroup(ItemGroup.MISC), hasDescription = true) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val pos = context.pos.offset(context.facing)

        return if (ModBlocks.wetFire.defaultState.canPlaceAt(world, pos)) {
            world.playSound(context.player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCK, 1f, 1f)
            world.setBlockState(pos, ModBlocks.wetFire.getPlacementState(ItemPlacementContext(context)), 11)

            (context.player as? ServerPlayerEntity)?.let {
                Criterions.PLACED_BLOCK.handle(it, pos, context.itemStack)
            }

            if (!world.isClient && context.player?.isCreative != true) {
                context.itemStack.subtractAmount(1)
            }

            ActionResult.SUCCESS
        } else ActionResult.FAILURE
    }
}
