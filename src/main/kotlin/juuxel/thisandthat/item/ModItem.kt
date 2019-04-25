/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.util.ModContent
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.TextComponent
import net.minecraft.text.TextFormat
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.world.World

open class ModItem(
    override val name: String,
    settings: Settings,
    private val hasDescription: Boolean = false,
    private val descriptionKey: String = "%TranslationKey.desc"
) : Item(settings), ModContent<Item> {
    override fun buildTooltip(
        stack: ItemStack?,
        world: World?,
        list: MutableList<TextComponent>, context: TooltipContext?) {
        super.buildTooltip(stack, world, list, context)
        if (hasDescription)
            list.add(TranslatableTextComponent(descriptionKey.replace("%TranslationKey", translationKey)).modifyStyle {
                it.isItalic = true
                it.color = TextFormat.DARK_GRAY
            })
    }
}
