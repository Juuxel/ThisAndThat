/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.multipart

import juuxel.thisandthat.util.BlockVariant
import juuxel.thisandthat.util.ModMultipart
import juuxel.thisandthat.util.MultipartUtils
import net.minecraft.block.Block
import net.minecraft.block.enums.BlockHalf
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.state.StateFactory
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringRepresentable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.ViewableWorld
import net.shadowfacts.simplemultipart.multipart.Multipart
import net.shadowfacts.simplemultipart.multipart.MultipartState
import net.shadowfacts.simplemultipart.multipart.MultipartView
import net.shadowfacts.simplemultipart.util.MultipartPlacementContext
import java.util.*
import kotlin.math.abs

class PostMultipart(variant: BlockVariant) : Multipart(), ModMultipart {
    override val name = "${variant.contentName}_post_multipart"
    override val hasDescription = true
    override val descriptionKey = "block.thisandthat.post.desc"
    override val itemSettings = Item.Settings().itemGroup(ItemGroup.DECORATIONS)

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

        fun tryCenter(fallback: Location): Location =
            if (!context.isOffset && context.container.canInsert(
                    defaultState.with(half, MultipartUtils.getHalf(context))
                        .with(location, Location.Center)
                )
            ) Location.Center
            else fallback

        val l = when (context.facing) {
            Direction.NORTH -> tryCenter(Location.North)
            Direction.EAST -> tryCenter(Location.East)
            Direction.SOUTH -> tryCenter(Location.South)
            Direction.WEST -> tryCenter(Location.West)
            else -> when {
                x > max && cx <= cz -> Location.East
                x < min && cx >= cz -> Location.West
                z > max -> Location.South
                z < min -> Location.North
                x in min..max && z in min..max -> Location.Center
                else -> Location.Center
            }
        }.let {
            if (context.facing.axis.isHorizontal && context.isOffset)
                it.opposite
            else it
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

    override fun canIntersectWith(self: MultipartState, other: MultipartState) =
        other.multipart is PlatformMultipart

    override fun canSupportTorches(state: MultipartState, world: ViewableWorld, pos: BlockPos) =
        state[half] == BlockHalf.TOP && state[location] == Location.Center

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
        val opposite get() = when (this) {
            North -> South
            South -> North
            East -> West
            West -> East
            Center -> Center
        }
    }
}
