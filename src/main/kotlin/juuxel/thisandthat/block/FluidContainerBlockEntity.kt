/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import io.github.prospector.silk.fluid.FluidContainer
import io.github.prospector.silk.fluid.FluidInstance
import juuxel.thisandthat.util.FluidEither
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry

abstract class FluidContainerBlockEntity internal constructor(
    private val capacity: Int,
    type: BlockEntityType<*>
) : BlockEntity(type), FluidContainer {
    protected var contents: FluidEither = FluidEither.Empty
    protected val fluid get() = contents.fluid
    protected val amount get() = contents.amount

    override fun extractFluid(direction: Direction, fluid: Fluid, amount: Int) {
        if (canExtractFluid(direction, fluid, amount)) {
            contents = FluidEither(fluid, this.amount - amount)
        }
    }

    override fun insertFluid(direction: Direction, fluid: Fluid, amount: Int) {
        if (canInsertFluid(direction, fluid, amount)) {
            contents = FluidEither(fluid, amount)
        }
    }

    override fun canExtractFluid(direction: Direction, fluid: Fluid, amount: Int) =
        this.fluid == fluid && this.amount >= amount

    override fun canInsertFluid(direction: Direction, fluid: Fluid, amount: Int) =
        this.fluid in arrayOf(Fluids.EMPTY, fluid) && this.amount + amount <= capacity

    override fun getMaxCapacity() = capacity
    override fun getFluids(p0: Direction?) = arrayOf(contents.toFluidInstance())
    override fun setFluid(p0: Direction?, p1: FluidInstance) {
        contents = FluidEither(p1)
    }

    override fun toTag(tag: CompoundTag) = super.toTag(tag).apply {
        putString("fluid", Registry.FLUID.getId(fluid).toString())
        putInt("amount", amount)
    }

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        val fluid = Registry.FLUID[Identifier(tag.getString("fluid"))]
        val amount = tag.getInt("amount")
        contents = FluidEither(fluid, amount)
    }
}