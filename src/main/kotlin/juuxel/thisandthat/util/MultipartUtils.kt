package juuxel.thisandthat.util

import net.minecraft.block.enums.BlockHalf
import net.minecraft.util.math.Direction
import net.shadowfacts.simplemultipart.util.MultipartPlacementContext

object MultipartUtils {
    fun getHalf(context: MultipartPlacementContext): BlockHalf {
        val hitSide = context.facing

        return when {
            hitSide === Direction.DOWN -> if (context.hitY >= 0.5f) BlockHalf.BOTTOM else BlockHalf.TOP
            hitSide === Direction.UP -> if (0.5f <= context.hitY && context.hitY < 1) BlockHalf.TOP else BlockHalf.BOTTOM
            else -> if (context.hitY >= 0.5f) BlockHalf.TOP else BlockHalf.BOTTOM
        }
    }
}