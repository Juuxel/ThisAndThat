package juuxel.thisandthat.multipart

import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModMultipart
import juuxel.thisandthat.util.MultipartUtils
import net.minecraft.block.Block
import net.minecraft.block.enums.BlockHalf
import net.minecraft.state.StateFactory
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringRepresentable
import net.minecraft.util.shape.VoxelShape
import net.shadowfacts.simplemultipart.multipart.Multipart
import net.shadowfacts.simplemultipart.multipart.MultipartState
import net.shadowfacts.simplemultipart.multipart.MultipartView
import net.shadowfacts.simplemultipart.util.MultipartPlacementContext
import java.util.*
import kotlin.math.roundToInt

// TODO variants
class PlatformMultipart(variant: BlockVariant) : Multipart(), ModMultipart {
    override val name = "${variant.contentName}_platform_multipart"

    init {
        defaultState = defaultState.with(location, Location.XN_ZN).with(half, BlockHalf.BOTTOM)
    }

    override fun getPlacementState(context: MultipartPlacementContext): MultipartState {
        val x = context.hitX.roundToInt() % 2
        val z = context.hitZ.roundToInt() % 2

        val l = Location.values().firstOrNull { it.x == x && it.z == z } ?: Location.XN_ZN

        return defaultState.with(half, MultipartUtils.getHalf(context)).with(location, l)
    }

    override fun getBoundingShape(state: MultipartState, view: MultipartView?) =
        shapes.getOrPut(state) {
            val x = state[location].x * 8
            val y = if (state[half] == BlockHalf.TOP) 8 else 0
            val z = state[location].z * 8

            Block.createCubeShape(0.0 + x, 6.0 + y, 0.0 + z, 8.0 + x, 8.0 + y, 8.0 + z)
        }

    override fun appendProperties(builder: StateFactory.Builder<Multipart, MultipartState>) {
        builder.with(location, half)
    }

    companion object {
        val location = EnumProperty.create("location", Location::class.java)
        val half = EnumProperty.create("half", BlockHalf::class.java)
        private val shapes = HashMap<MultipartState, VoxelShape>()
    }

    enum class Location(val x: Int, val z: Int) : StringRepresentable {
        XP_ZP(1, 1),
        XP_ZN(1, 0),
        XN_ZP(0, 1),
        XN_ZN(0, 0);

        override fun asString() = name.toLowerCase(Locale.ROOT)
    }
}
