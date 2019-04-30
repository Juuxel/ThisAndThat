/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.item

import io.github.juuxel.polyester.item.PolyesterBaseItem
import net.minecraft.item.ItemGroup

class StoneRodItem : PolyesterBaseItem("stone_rod", Settings().itemGroup(ItemGroup.MISC)) {
    override val hasDescription = true
}
