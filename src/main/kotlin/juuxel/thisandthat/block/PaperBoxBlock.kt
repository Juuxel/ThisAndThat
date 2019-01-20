/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.container.SingleSlotContainer
import juuxel.thisandthat.lib.ModContainers
import juuxel.thisandthat.util.ModBlock
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.LockableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.NameTagItem
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.BlockHitResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class PaperBoxBlock : BlockWithEntity(Settings.of(Material.WOOL)), ModBlock {
    override val name = "paper_box"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val blockEntityType = BLOCK_ENTITY_TYPE

    override fun createBlockEntity(view: BlockView) = PaperBoxBE()

    override fun activate(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hitResult: BlockHitResult): Boolean {
        if (!world.isClient) {
            val stack = player.getStackInHand(hand)

            if (stack.item !is NameTagItem) {
                ContainerProviderRegistry.INSTANCE.openContainer(ModContainers.paperBox, player) {
                    it.writeBlockPos(pos)
                }
            } else {
                (world.getBlockEntity(pos) as PaperBoxBE).customName = stack.displayName
                stack.subtractAmount(1)
            }
        }

        return true
    }

    override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL

    companion object {
        val BLOCK_ENTITY_TYPE = BlockEntityType(::PaperBoxBE, null)
    }

    class PaperBoxBE private constructor(private val inventory: Inventory) : LockableContainerBlockEntity(BLOCK_ENTITY_TYPE), Inventory by inventory {
        constructor() : this(BasicInventory(1))

        override fun markDirty() {
            super.markDirty()
            inventory.markDirty()
        }

        override fun getInvMaxStackAmount() = 8
        override fun createContainer(syncId: Int, playerInv: PlayerInventory) =
            SingleSlotContainer(syncId, this, playerInv)
        override fun method_17823() = TranslatableTextComponent("container.thisandthat.paper_box")
    }
}