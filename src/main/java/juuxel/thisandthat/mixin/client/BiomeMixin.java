/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin.client;

import juuxel.thisandthat.lib.ModConfig;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Biome.class)
public class BiomeMixin {
    @ModifyArg(method = "getSkyColor", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;hsvToRgb(FFF)I"))
    private float modifySkyHue(float hue) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return (hue - 0.62222224F + tweaks.skyHue) % 360f;
        else
            return hue;
    }

    @ModifyArg(method = "getSkyColor", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;hsvToRgb(FFF)I"))
    private float modifySkySaturation(float saturation) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return (saturation - 0.5F + tweaks.skySaturation) % 360f;
        else
            return saturation;
    }

    @ModifyArg(method = "getSkyColor", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;hsvToRgb(FFF)I"))
    private float modifySkyBrightness(float brightness) {
        ModConfig.Tweaks tweaks = ModConfig.getInstance().tweaks;

        if (tweaks.changeSkyColor)
            return (brightness - 1F + tweaks.skyBrightness) % 360f;
        else
            return brightness;
    }
}
