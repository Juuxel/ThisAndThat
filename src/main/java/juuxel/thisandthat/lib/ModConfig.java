/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.lib;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import juuxel.thisandthat.ThisAndThat;
import juuxel.thisandthat.util.CloudColorMode;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.util.Lazy;

import java.io.File;

@ConfigFile(name = "ThisAndThat")
public final class ModConfig {
//    private static final Lazy<ModConfig> LAZY_INSTANCE = new Lazy<>(() -> ConfigManager.loadConfig(ModConfig.class));
    // TODO: Use the Cotton ConfigManager if it's fixed
    private static final Lazy<ModConfig> LAZY_INSTANCE = new Lazy<>(() -> {
        try {
            ThisAndThat.logger.info("Loading config");
            Jankson jankson = Jankson.builder().build();
            JsonObject obj = jankson.load(new File(FabricLoader.INSTANCE.getConfigDirectory(), "ThisAndThat.conf"));
            return jankson.fromJson(obj, ModConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load TT config", e);
        }
    });
    public Items items = new Items();
    public Modules modules = new Modules();
    public Tweaks tweaks = new Tweaks();

    public static ModConfig getInstance() {
        return LAZY_INSTANCE.get();
    }

    public static final class Items {
        public boolean enderFeathers = true;
    }

    public static final class Modules {
        public boolean multiparts = true;
    }

    public static final class Tweaks {
        public float skyHue = 0.62222224f;
        public float skySaturation = 0.5f;
        public float skyBrightness = 1f;
        public boolean changeSkyColor = false;
        public double cloudRedModifier = 0.0;
        public double cloudGreenModifier = 0.0;
        public double cloudBlueModifier = 0.0;
        public CloudColorMode cloudColorMode = CloudColorMode.Vanilla;
    }
}
