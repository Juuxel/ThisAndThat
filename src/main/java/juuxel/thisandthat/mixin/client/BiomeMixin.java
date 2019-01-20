/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin.client;

import juuxel.thisandthat.lib.ModConfig;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Biome.class)
public class BiomeMixin {
    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = 0.62222224f), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F")))
    private float modifySkyHue(float hue) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return tweaks.skyHue % 360f;
        else
            return hue;
    }

    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = 0.5f), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F")))
    private float modifySkySaturation(float saturation) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return tweaks.skySaturation % 360f;
        else
            return saturation;
    }

    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = 1f), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F")))
    private float modifySkyBrightness(float brightness) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return tweaks.skyBrightness % 360f;
        else
            return brightness;
    }
}
