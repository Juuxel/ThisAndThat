/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import juuxel.thisandthat.saw.SawRecipeLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final private ReloadableResourceManager dataManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruct(File f, Proxy p, DataFixer df, ServerCommandManager scm,
                             YggdrasilAuthenticationService yas, MinecraftSessionService mss, GameProfileRepository r,
                             UserCache c, CallbackInfo info) {
        dataManager.addListener(SawRecipeLoader.INSTANCE);
    }
}
