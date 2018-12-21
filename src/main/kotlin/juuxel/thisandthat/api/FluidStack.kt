/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.api

import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids

data class FluidStack(val fluid: Fluid, val amount: Int) {
    init {
        require(amount >= 0)
    }

    companion object {
        val EMPTY = FluidStack(Fluids.EMPTY, 0)
        const val AMOUNT_BUCKET = 1620
    }
}
