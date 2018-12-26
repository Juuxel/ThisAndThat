/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import io.github.prospector.silk.fluid.FluidInstance
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids

sealed class FluidEither {
    abstract val fluid: Fluid
    abstract val amount: Int

    fun toFluidInstance() = FluidInstance(fluid, amount)

    data class Some(override val fluid: Fluid, override val amount: Int) : FluidEither()
    object Empty : FluidEither() {
        override val fluid = Fluids.EMPTY
        override val amount = 0
    }

    companion object {
        operator fun invoke(instance: FluidInstance) = invoke(instance.fluid, instance.amount)
        operator fun invoke(fluid: Fluid, amount: Int) = when (amount) {
            0 -> Empty
            else -> Some(fluid, amount)
        }
    }
}