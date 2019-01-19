/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

/*import juuxel.thisandthat.util.ModContent
import juuxel.thisandthat.util.ModMultipart
import net.minecraft.client.item.TooltipOptions
import net.minecraft.item.ItemStack
import net.minecraft.text.TextComponent
import net.minecraft.text.TextFormat
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.world.World
import net.shadowfacts.simplemultipart.item.MultipartItem
import net.minecraft.util.SystemUtil
import net.minecraft.util.registry.Registry

class ModMultipartItem(multipart: ModMultipart) :
    MultipartItem(multipart.unwrap(), multipart.itemSettings), ModContent<MultipartItem> {
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
}*/
