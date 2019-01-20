/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.container

import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory

class SingleSlotContainer(syncId: Int, inv: Inventory, playerInv: PlayerInventory) : TTContainer(syncId, playerInv) {
    init {
        this.addSlot(Slot(inv, 0, 80, 35))
    }

    override fun canUse(player: PlayerEntity?) = true
}
