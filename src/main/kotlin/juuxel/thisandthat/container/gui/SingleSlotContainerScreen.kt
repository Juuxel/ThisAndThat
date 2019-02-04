/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.container.gui

import com.mojang.blaze3d.platform.GlStateManager
import juuxel.thisandthat.container.SingleSlotContainer
import net.minecraft.client.gui.ContainerScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.Identifier

class SingleSlotContainerScreen(syncId: Int, inventory: Inventory, player: PlayerEntity, id: String) :
    ContainerScreen<SingleSlotContainer>(
        SingleSlotContainer(syncId, inventory, player.inventory),
        player.inventory,
        TranslatableTextComponent("container.thisandthat.$id")
    ) {
    private val background = Identifier("thisandthat", "textures/gui/$id.png")

    override fun drawBackground(v: Float, i: Int, i1: Int) {
        GlStateManager.color4f(1f, 1f, 1f, 1f)
        client.textureManager.bindTexture(background)
        drawTexturedRect(left, top, 0, 0, containerWidth, containerHeight)
    }

    override fun drawForeground(int_1: Int, int_2: Int) {
        val nameText = this.name.formattedText
        this.fontRenderer.draw(
            nameText,
            (this.containerWidth / 2 - this.fontRenderer.getStringWidth(nameText) / 2).toFloat(),
            6.0f,
            4210752
        )
        this.fontRenderer.draw(
            this.playerInventory.displayName.formattedText,
            8.0f,
            (this.containerHeight - 96 + 2).toFloat(),
            4210752
        )
    }
}
