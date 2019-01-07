/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.saw

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

typealias SawPredicate = (BlockState) -> Boolean
typealias SawTransform = (BlockState) -> List<ItemStack>
