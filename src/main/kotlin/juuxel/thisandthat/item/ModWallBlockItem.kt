/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.util.ModBlock
import juuxel.thisandthat.util.ModContent
import net.minecraft.client.item.TooltipOptions
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.item.block.WallStandingBlockItem
import net.minecraft.text.TextComponent
import net.minecraft.text.TextFormat
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.world.World

class ModWallBlockItem(ground: ModBlock, wall: ModBlock) :
    WallStandingBlockItem(ground.unwrap(), wall.unwrap(), ground.itemSettings),
    ModContent<BlockItem> {
    override val name = ground.name
    private val hasDescription = ground.hasDescription
    private val descriptionKey = ground.descriptionKey

    override fun buildTooltip(p0: ItemStack?, p1: World?, list: MutableList<TextComponent>, p3: TooltipOptions?) {
        if (hasDescription)
            list.add(
                TranslatableTextComponent(
                    descriptionKey.replace(
                        "%TranslationKey",
                        translationKey
                    )
                ).modifyStyle {
                it.isItalic = true
                it.color = TextFormat.DARK_GRAY
            })
    }
}
