/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

/**
 * Extending `net.minecraft.world.loot.TagEntry` with selecting
 * a random item from a tag.
 */
interface TagEntryExtensions {
    var isRandom: Boolean
}
