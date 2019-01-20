/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin.client;

import juuxel.thisandthat.lib.ModConfig;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin {
    @Inject(method = "getCloudColor", at = @At("RETURN"), cancellable = true)
    private void getCloudColor(float float_1, CallbackInfoReturnable<Vec3d> info) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeCloudColor) {
            Vec3d original = info.getReturnValue();
            info.setReturnValue(new Vec3d(
                    MathHelper.clamp(original.x + tweaks.cloudRedModifier, 0, 1),
                    MathHelper.clamp(original.y + tweaks.cloudGreenModifier, 0, 1),
                    MathHelper.clamp(original.z + tweaks.cloudBlueModifier, 0, 1)
            ));
        }
    }
}
