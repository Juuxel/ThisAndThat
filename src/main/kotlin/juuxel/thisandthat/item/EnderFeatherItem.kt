/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class EnderFeatherItem : ModItem("ender_feather", Settings().itemGroup(ItemGroup.TRANSPORTATION).durability(500)) {
    init {
        addProperty(Identifier("activated")) { stack, _, _ ->
            if (stack.tag?.getBoolean("activated") == true) 1f else 0f
        }
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (player.getStackInHand(hand).damage < durability) {
            val stack = player.getStackInHand(hand)
            val previous = stack.getOrCreateTag().getBoolean("activated")

            if (previous || (!previous && !player.onGround)) {
                stack.tag!!.putBoolean("activated", !previous)
                player.abilities.flying = !previous
            }

            if (player.onGround) {
                world.playSound(player, player.pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYER, 1f, 1f)
            } else if (!previous) {
                world.playSound(player, player.pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYER, 1f, 1f)
                for (i in 1..15)
                    world.addParticle(
                        ParticleTypes.WITCH,
                        player.x, player.y + 1, player.z,
                        Math.random() * 8 - 1, Math.random() * 4 - 1, Math.random() * 8 - 1
                    )
            }
        }

        return super.use(world, player, hand)
    }

    override fun hasEnchantmentGlow(stack: ItemStack) =
        stack.tag?.getBoolean("activated") ?: false

    override fun onUpdate(stack: ItemStack, world: World, entity: Entity, p3: Int, p4: Boolean) {
        if (entity !is PlayerEntity) return

        if (world is ServerWorld && entity.abilities.flying && stack.tag?.getBoolean("activated") == true && stack.damage < durability && random.nextInt(10) == 0) {
            stack.damage++
        }

        if (stack.damage >= durability) {
            stack.getOrCreateTag().putBoolean("activated", false)
        }

        val activated = stack.tag?.getBoolean("activated") ?: return

        if (entity.abilities.flying) return

        if (!activated)
            entity.abilities.flying = false

        if (!entity.abilities.flying) {
            stack.tag?.putBoolean("activated", false)
            return
        }
    }

    override fun canRepair(p0: ItemStack?, p1: ItemStack): Boolean {
        return p1.item == Items.ENDER_PEARL || super.canRepair(p0, p1)
    }
}
