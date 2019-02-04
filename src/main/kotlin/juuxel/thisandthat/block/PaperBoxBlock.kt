/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.container.SingleSlotContainer
import juuxel.thisandthat.lib.ModContainers
import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.TTBasicInventory
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.fabricmc.fabric.block.FabricBlockSettings
import net.fabricmc.fabric.tags.FabricItemTags
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.HopperBlockEntity
import net.minecraft.block.entity.LockableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.NameTagItem
import net.minecraft.nbt.CompoundTag
import net.minecraft.sortme.ItemScatterer
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.InventoryUtil
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class PaperBoxBlock : BlockWithEntity(SETTINGS), ModBlock {
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

    override fun onBlockRemoved(state1: BlockState, world: World, pos: BlockPos, state2: BlockState, boolean_1: Boolean) {
        if (state1.block != state2.block) {
            HopperBlockEntity.getInventoryAt(world, pos)?.let {
                ItemScatterer.spawn(world, pos, it)
                world.updateHorizontalAdjacent(pos, this)
            }

            super.onBlockRemoved(state1, world, pos, state2, boolean_1)
        }
    }

//    override fun getRenderType(state: BlockState?) = BlockRenderType.MODEL

    companion object {
        val BLOCK_ENTITY_TYPE = BlockEntityType(::PaperBoxBE, null)
        private val SETTINGS = FabricBlockSettings.of(Material.WOOL)
            .sounds(BlockSoundGroup.WOOL)
            .strength(0.25f, 0.25f)
            .breakByHand(true)
            .breakByTool(FabricItemTags.AXES)
            .materialColor(DyeColor.WHITE)
            .build()
    }

    class PaperBoxBE private constructor(private val inventory: TTBasicInventory) : LockableContainerBlockEntity(BLOCK_ENTITY_TYPE), Inventory by inventory {
        constructor() : this(TTBasicInventory(1))

        override fun markDirty() {
            super.markDirty()
            inventory.markDirty()
        }

        override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
            inventory.toTag(tag)
        }

        override fun fromTag(tag: CompoundTag) {
            inventory.fromTag(tag)
        }

        override fun getInvMaxStackAmount() = 8
        override fun createContainer(syncId: Int, playerInv: PlayerInventory) =
            SingleSlotContainer(syncId, this, playerInv)
        override fun getContainerName() = TranslatableTextComponent("container.thisandthat.paper_box")
    }
}