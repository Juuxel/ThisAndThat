/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ViewableWorld
import net.shadowfacts.simplemultipart.multipart.Multipart
import net.shadowfacts.simplemultipart.multipart.MultipartState

interface ModContent<out T> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    fun unwrap(): T = this as T
}

interface BlockLikeContent<out T> : ModContent<T> {
    val registerItem: Boolean get() = true
    val hasDescription: Boolean get() = false
    val descriptionKey: String get() = "%TranslationKey.desc"
    val itemSettings: Item.Settings?
}

interface ModBlock : BlockLikeContent<Block> {
    val blockEntityType: BlockEntityType<*>? get() = null
}

interface ModMultipart : BlockLikeContent<Multipart> {
    fun canSupportTorches(state: MultipartState, world: ViewableWorld, pos: BlockPos) = false
}
