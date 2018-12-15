/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.block

import juuxel.thisandthat.lib.ModItems
import juuxel.thisandthat.util.ModBlock
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock

class RyeCropBlock : CropBlock(Settings.copy(Blocks.WHEAT)), ModBlock {
    override val name = "rye_crop"
    override val itemSettings = null
    override val registerItem = false

    override fun getCropItem() = ModItems.ryeSeeds.unwrap()
}
