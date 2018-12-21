/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.api

import net.minecraft.fluid.Fluid

sealed class FluidStack2(val amount: Int) {
    object Empty : FluidStack2(0)
    class Stack(val fluid: Fluid, amount: Int) : FluidStack2(amount)
}

fun FluidStack.toFS2() = if (amount == 0) FluidStack2.Empty else FluidStack2.Stack(fluid, amount)
