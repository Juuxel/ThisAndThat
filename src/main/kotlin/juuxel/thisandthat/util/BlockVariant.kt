/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.block.Block
import net.minecraft.block.Blocks

interface BlockVariant {
    val contentName: String
    val settings: Block.Settings

    enum class Wood(override val contentName: String) : BlockVariant {
        Oak("oak"), Spruce("spruce"), Birch("birch"), Jungle("jungle"), Acacia("acacia"), DarkOak("dark_oak");

        override val settings = Block.Settings.copy(Blocks.OAK_FENCE)
    }

    enum class Stone(override val contentName: String) : BlockVariant {
        SmoothStone("stone"), Cobblestone("cobblestone"), Sandstone("sandstone"),
        Diorite("diorite"), Andesite("andesite"), Granite("granite");

        override val settings = Block.Settings.copy(Blocks.COBBLESTONE_WALL)
    }
}
