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
import kotlin.math.abs

// TODO variants
class PostMultipart(variant: BlockVariant) : Multipart(), ModMultipart {
    override val name = "${variant.contentName}_post_multipart"

    init {
        defaultState = defaultState.with(location, Location.Center).with(half, BlockHalf.BOTTOM)
    }

    override fun getPlacementState(context: MultipartPlacementContext): MultipartState {
        val min = 0.4f
        val max = 1f - min
        val x = context.hitX
        val z = context.hitZ

        val cx = abs(8f - x)
        val cz = abs(8f - z)

        val l = when {
            x > max && cx <= cz -> Location.East
            x < min && cx >= cz -> Location.West
            z > max -> Location.South
            z < min -> Location.North
            x in min..max && z in min..max -> Location.Center
            else -> Location.Center
        }

        return defaultState.with(half, MultipartUtils.getHalf(context)).with(location, l)
    }

    override fun getBoundingShape(state: MultipartState, view: MultipartView?) =
        shapes.getOrPut(state) {
            val x = state[location].xOffset * 4
            val y = if (state[half] == BlockHalf.TOP) 8 else 0
            val z = state[location].zOffset * 4

            Block.createCubeShape(6.0 + x, 0.0 + y, 6.0 + z, 10.0 + x, 8.0 + y, 10.0 + z)
        }

    override fun appendProperties(builder: StateFactory.Builder<Multipart, MultipartState>) {
        builder.with(location, half)
    }

    /*override*/ fun canIntersectWith(self: MultipartState, other: MultipartState) =
        other.multipart is PlatformMultipart

    companion object {
        val location = EnumProperty.create("location", Location::class.java)
        val half = EnumProperty.create("half", BlockHalf::class.java)
        private val shapes = HashMap<MultipartState, VoxelShape>()
    }

    enum class Location(val xOffset: Int = 0, val zOffset: Int = 0) : StringRepresentable {
        North(zOffset = -1),
        East(xOffset = 1),
        South(zOffset = 1),
        West(xOffset = -1),
        Center;

        override fun asString() = name.toLowerCase(Locale.ROOT)
    }
}
