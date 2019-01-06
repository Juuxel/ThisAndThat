/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.util.ModContent
import juuxel.thisandthat.util.ModMultipart
import net.minecraft.client.item.TooltipOptions
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.text.TextComponent
import net.minecraft.text.TextFormat
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.ActionResult
import net.minecraft.world.World
import net.shadowfacts.simplemultipart.item.MultipartItem
import net.minecraft.block.Blocks
import juuxel.thisandthat.util.TTMultipartPlacementContext
import net.minecraft.util.SystemUtil
import net.minecraft.util.registry.Registry

// TODO Item settings, requires SimpleMultipart update
class ModMultipartItem(multipart: ModMultipart)
    : MultipartItem(multipart.unwrap()/*, multipart.itemSettings*/), ModContent<MultipartItem> {
    override val name = multipart.name
    private val hasDescription = multipart.hasDescription
    private val descriptionKey = multipart.descriptionKey
    private val _translationKey by lazy {
        SystemUtil.createTranslationKey("multipart", Registry.ITEM.getId(this))
    }

    override fun getOrCreateTranslationKey() = _translationKey

    override fun buildTooltip(p0: ItemStack?, p1: World?, list: MutableList<TextComponent>, p3: TooltipOptions?) {
        list.add(TranslatableTextComponent("desc.thisandthat.multipart").modifyStyle {
            it.color = TextFormat.DARK_RED
        })

        if (hasDescription)
            list.add(TranslatableTextComponent(descriptionKey.replace("%TranslationKey", translationKey)).modifyStyle {
                it.isItalic = true
                it.color = TextFormat.DARK_GRAY
            })
    }

    // TODO Remove on SimpleMultipart update
    override fun tryPlace(context: ItemUsageContext): ActionResult {
        // If a multipart inside an existing container was clicked, try inserting into that
        val hitContainer = getContainer(context)
        if (hitContainer != null && tryPlace(TTMultipartPlacementContext(hitContainer, context, false))) {
            return ActionResult.SUCCESS
        }

        // Otherwise, get or create a new container and try inserting into that
        val offsetContext = ItemUsageContext(
            context.player,
            context.itemStack,
            context.pos.offset(context.facing),
            context.facing,
            context.hitX,
            context.hitY,
            context.hitZ
        )
        val offsetContainer = getOrCreateContainer(offsetContext)
        if (offsetContainer != null) {
            if (tryPlace(TTMultipartPlacementContext(offsetContainer, offsetContext, true))) {
                return ActionResult.SUCCESS
            } else {
                // if the a new container was created, and no part was inserted, remove the empty container
                if (!offsetContainer.hasParts()) {
                    context.world.setBlockState(offsetContext.pos, Blocks.AIR.defaultState)
                }
            }
        }

        return ActionResult.FAILURE
    }
}
