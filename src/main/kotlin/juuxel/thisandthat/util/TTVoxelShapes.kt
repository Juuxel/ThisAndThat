/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.util.math.BoundingBox
import net.minecraft.util.shape.VoxelShape

object TTVoxelShapes {
    @JvmStatic
    fun containedIn(outer: VoxelShape, inner: BoundingBox) = outer.boundingBoxList.any {
        it.minX <= inner.minX && it.maxX >= inner.maxX &&
            it.minY <= inner.minY && it.maxY >= inner.maxY &&
            it.minZ <= inner.minZ && it.maxZ >= inner.maxZ
    }
}