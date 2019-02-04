/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.nbt.CompoundTag

interface Serializable {
    fun fromTag(tag: CompoundTag)
    fun toTag(tag: CompoundTag): CompoundTag
}
