/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import juuxel.thisandthat.item.EnderFeatherItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ItemDropsMixin {
    @Shadow
    public PlayerAbilities abilities;

    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract void addChatMessage(TextComponent var1, boolean var2);

    @Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            cancellable = true)
    private void onDropItem(ItemStack stack, boolean b1, boolean b2, CallbackInfoReturnable<ItemEntity> info) {
        if (stack.getItem() instanceof EnderFeatherItem && !isCreative() && !isSpectator() &&
            abilities.flying) {
            addChatMessage(new TranslatableTextComponent("item.thisandthat.ender_feather.flying_drop"), true);
            inventory.insertStack(stack);
            info.setReturnValue(null);
        }
    }
}
