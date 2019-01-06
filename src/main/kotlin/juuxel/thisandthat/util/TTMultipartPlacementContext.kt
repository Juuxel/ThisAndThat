package juuxel.thisandthat.util

import net.minecraft.item.ItemUsageContext
import net.shadowfacts.simplemultipart.container.MultipartContainer
import net.shadowfacts.simplemultipart.util.MultipartPlacementContext

class TTMultipartPlacementContext(container: MultipartContainer, context: ItemUsageContext, val isOffset: Boolean) :
    MultipartPlacementContext(container, context)
