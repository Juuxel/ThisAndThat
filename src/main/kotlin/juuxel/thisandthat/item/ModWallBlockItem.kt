/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import io.github.juuxel.polyester.registry.HasDescription
import io.github.juuxel.polyester.registry.PolyesterBlock
import io.github.juuxel.polyester.registry.PolyesterItem
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.item.WallStandingBlockItem
import net.minecraft.text.TextComponent
import net.minecraft.world.World

class ModWallBlockItem(ground: PolyesterBlock, wall: PolyesterBlock, settings: Settings) : WallStandingBlockItem(
    ground.unwrap(),
    wall.unwrap(),
    settings
), PolyesterItem, HasDescription by ground {
    override val name = ground.name

    override fun buildTooltip(p0: ItemStack?, p1: World?, list: MutableList<TextComponent>, p3: TooltipContext?) {
        super.buildTooltip(p0, p1, list, p3)
        PolyesterItem.appendTooltipToList(list, this)
    }
}
