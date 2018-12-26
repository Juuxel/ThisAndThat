/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import io.github.prospector.silk.fluid.FluidInstance
import net.minecraft.fluid.Fluid

operator fun FluidInstance.component1(): Fluid = fluid
operator fun FluidInstance.component2() = amount
