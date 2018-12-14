/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.ModContent
import net.minecraft.block.Waterloggable
import net.minecraft.client.item.TooltipOptions
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.text.TextComponent
import net.minecraft.text.TextFormat
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.world.World

class ModBlockItem(block: ModBlock)
    : BlockItem(block.unwrap(), block.itemSettings), ModContent<BlockItem> {
    override val name = block.name
    private val hasDescription = block.hasDescription
    private val descriptionKey = block.descriptionKey
    private val isWaterloggable = block is Waterloggable

    override fun addInformation(p0: ItemStack?, p1: World?, list: MutableList<TextComponent>, p3: TooltipOptions?) {
        if (isWaterloggable)
            list.add(TranslatableTextComponent("desc.thisandthat.waterloggable").modifyStyle {
                it.color = TextFormat.BLUE
            })

        if (hasDescription)
            list.add(TranslatableTextComponent(descriptionKey.replace("%TranslationKey", translationKey)).modifyStyle {
                it.isItalic = true
                it.color = TextFormat.DARK_GRAY
            })
    }
}
