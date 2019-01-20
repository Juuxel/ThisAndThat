/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.container

import net.minecraft.container.Container
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerInventory

abstract class TTContainer(syncId: Int, playerInv: PlayerInventory) : Container(null, syncId) {
    init {
        for (row in 0..2) {
            for (col in 0..8) {
                addSlot(Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }

        for (i in 0..8) {
            addSlot(Slot(playerInv, i, 8 + i * 18, 142))
        }
    }
}
