/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.DefaultedList
import net.minecraft.util.InventoryUtil

open class TTBasicInventory(private val size: Int) : Inventory, Serializable {
    private val stackList = DefaultedList.create<ItemStack>(size, ItemStack.EMPTY)

    // implements Inventory
    override fun getInvStack(slot: Int) =
        if (slot in 0..stackList.lastIndex)
            stackList[slot]
        else
            ItemStack.EMPTY

    override fun markDirty() {}

    override fun setInvStack(slot: Int, stack: ItemStack) {
        stackList[slot] = stack

        if (!stack.isEmpty && stack.amount > invMaxStackAmount)
            stack.amount = invMaxStackAmount

        markDirty()
    }

    override fun removeInvStack(slot: Int): ItemStack {
        val stack = stackList[slot]
        stackList[slot] = ItemStack.EMPTY
        return stack
    }

    override fun canPlayerUseInv(player: PlayerEntity?) = true

    override fun clearInv() {
        stackList.clear()
    }

    override fun getInvSize() = size

    override fun takeInvStack(var1: Int, var2: Int) = InventoryUtil.splitStack(stackList, var1, var2).also {
        if (!it.isEmpty)
            markDirty()
    }

    override fun isInvEmpty() = stackList.any { !it.isEmpty }

    // implements Serializable
    override fun fromTag(tag: CompoundTag) {
        InventoryUtil.deserialize(tag, stackList)
    }

    override fun toTag(tag: CompoundTag) = tag.also {
        InventoryUtil.serialize(it, stackList)
    }
}
