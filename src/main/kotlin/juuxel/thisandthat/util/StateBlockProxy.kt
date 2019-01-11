/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.block.BlockState
import net.minecraft.block.pattern.BlockProxy
import net.minecraft.util.math.BlockPos

class StateBlockProxy(private val state: BlockState) : BlockProxy(null, BlockPos.ORIGIN, false) {
    override fun getBlockEntity() = null
    override fun getBlockState() = state
}
