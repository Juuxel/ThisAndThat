/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import juuxel.thisandthat.lib.ModItems;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public class BurnTimeMixin {
    @Inject(method = "createBurnableMap", at = @At("RETURN"))
    private static void addBurnTimes(CallbackInfoReturnable<Map<Item, Integer>> info) {
        Map<Item, Integer> map = info.getReturnValue();
        map.put(ModItems.INSTANCE.getPrismarineCoal().unwrap(), map.get(Items.COAL) * 3 / 2);
    }
}
