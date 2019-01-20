/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib

import juuxel.thisandthat.container.SingleSlotContainer
import juuxel.thisandthat.container.gui.SingleSlotContainerGui
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.entity.HopperBlockEntity
import net.minecraft.util.Identifier

object ModContainers {
    val paperBox = Identifier("thisandthat", "paper_box")

    fun init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(paperBox) { syncId, id, player, buf ->
            val pos = buf.readBlockPos()
            val inventory = HopperBlockEntity.getInventoryAt(player.world, pos)

            if (inventory != null)
                SingleSlotContainer(syncId, inventory, player.inventory)
            else
                null
        }
    }

    fun initClient() {
        GuiProviderRegistry.INSTANCE.registerFactory(paperBox) { syncId, id, player, buf ->
            val pos = buf.readBlockPos()
            val inventory = HopperBlockEntity.getInventoryAt(player.world, pos)

            if (inventory != null)
                SingleSlotContainerGui(syncId, inventory, player, paperBox.path)
            else
                null
        }
    }
}