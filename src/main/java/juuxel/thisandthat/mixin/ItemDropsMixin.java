/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import juuxel.thisandthat.item.EnderFeatherItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ItemDropsMixin {
    @Shadow
    private PlayerAbilities abilities;

    @Shadow
    abstract boolean isCreative();

    @Shadow
    abstract boolean isSpectator();

    @Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    private void onDropItem(ItemStack stack, boolean b1, boolean b2, CallbackInfoReturnable<ItemEntity> info) {
        if (stack.getItem() instanceof EnderFeatherItem && !isCreative() && !isSpectator()) {
            stack.getOrCreateTag().putBoolean("activated", false);
            abilities.flying = false;
            // TODO Figure out why this doesn't work
        }
    }
}
