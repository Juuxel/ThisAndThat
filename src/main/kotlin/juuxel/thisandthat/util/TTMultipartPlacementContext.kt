/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.util

import net.minecraft.item.ItemUsageContext
import net.shadowfacts.simplemultipart.container.MultipartContainer
import net.shadowfacts.simplemultipart.util.MultipartPlacementContext

// TODO Remove on SimpleMultipart update
@Deprecated("Replace with MultiplacePlacementContext")
class TTMultipartPlacementContext(container: MultipartContainer, context: ItemUsageContext, val isOffset: Boolean) :
    MultipartPlacementContext(container, context)
