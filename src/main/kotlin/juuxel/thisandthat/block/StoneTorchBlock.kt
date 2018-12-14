/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.modify
import net.minecraft.block.Blocks
import net.minecraft.block.TorchBlock
import net.minecraft.block.WallTorchBlock
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup

class StoneTorchBlock : TorchBlock(settings), ModBlock {
    override val name = "stone_torch"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)
    override val registerItem = false
    override val hasDescription = true

    class Wall(settings: Settings) : WallTorchBlock(settings), ModBlock {
        override val name = "wall_stone_torch"
        override val itemSettings = null
        override val registerItem = false
    }

    companion object {
        internal val settings = Settings.copy(Blocks.TORCH).modify(sounds = BlockSoundGroup.STONE, lightLevel = 15)
    }
}
