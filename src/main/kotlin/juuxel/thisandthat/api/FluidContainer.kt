/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.api

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

interface FluidContainer {
    val capacity: Int
    val insertableAmount: TransferAmount

    fun canInsert(world: World, pos: BlockPos, state: BlockState, side: Direction, stack: FluidStack): Boolean
    fun canExtract(world: World, pos: BlockPos, state: BlockState, side: Direction, amount: TransferAmount): Boolean
    fun canExtract(world: World, pos: BlockPos, state: BlockState, side: Direction, amount: Int): Boolean =
        canExtract(world, pos, state, side, TransferAmount.exact(amount))

    fun extract(world: World, pos: BlockPos, state: BlockState, side: Direction, amount: TransferAmount, simulated: Boolean = false): FluidStack
    fun extract(world: World, pos: BlockPos, state: BlockState, side: Direction, amount: Int, simulated: Boolean = false): FluidStack =
        extract(world, pos, state, side, TransferAmount.exact(amount), simulated)
    fun insert(world: World, pos: BlockPos, state: BlockState, side: Direction, stack: FluidStack): Boolean

//    fun canConnect(side: Direction): Boolean
}
