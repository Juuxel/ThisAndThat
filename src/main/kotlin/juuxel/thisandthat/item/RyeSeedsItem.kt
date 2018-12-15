/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import juuxel.thisandthat.lib.ModBlocks
import juuxel.thisandthat.util.ModContent
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.SeedsItem

class RyeSeedsItem : SeedsItem(ModBlocks.ryeCrop, Item.Settings().itemGroup(ItemGroup.MISC)), ModContent<Item> {
    override val name = "rye_seeds"
}
