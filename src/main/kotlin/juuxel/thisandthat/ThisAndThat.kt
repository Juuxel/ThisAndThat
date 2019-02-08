/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat

import io.github.cottonmc.cotton.logging.Ansi
import io.github.cottonmc.cotton.logging.ModLogger
import juuxel.thisandthat.block.WetFireBlock
import juuxel.thisandthat.lib.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.events.PlayerInteractionEvent
import net.minecraft.util.ActionResult

object ThisAndThat : ModInitializer {
    internal val logger = ModLogger(ThisAndThat::class.java, "ThisAndThat").apply {
        setPrefixFormat(Ansi.Cyan.and(Ansi.Bold))
    }

    override fun onInitialize() {
        ModBlocks.init()
        ModItems.init()
        ModMultiparts.init()
        ModTags.init()
//        ModContainers.init()
        PlayerInteractionEvent.ATTACK_BLOCK.register(
            PlayerInteractionEvent.Block { player, world, _, pos, direction ->
                val offset = pos.offset(direction)
                if (world.getBlockState(offset).block is WetFireBlock) {
                    world.fireWorldEvent(player, 1009, offset, 0)
                    world.clearBlockState(offset)
                    ActionResult.SUCCESS
                }
                ActionResult.PASS
            }
        )
    }

    object Client : ClientModInitializer {
        override fun onInitializeClient() {
//            ModContainers.initClient()
        }
    }
}
