package juuxel.thisandthat.mixin;

import juuxel.thisandthat.util.MultipartTags;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.RegistryTagContainer;
import net.minecraft.tag.TagManager;
import net.minecraft.util.PacketByteBuf;
import net.shadowfacts.simplemultipart.SimpleMultipart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagManager.class)
public class MultipartTagMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        MultipartTags.containers.put((TagManager) (Object) this, new RegistryTagContainer<>(SimpleMultipart.MULTIPART, "tags/multiparts", "multipart"));
    }

    @Inject(method = "clear", at = @At("RETURN"))
    private void onClear(CallbackInfo info) {
        MultipartTags.containers.get(this).clear();
    }

    @Inject(method = "onResourceReload", at = @At("RETURN"))
    private void onResourceReload(ResourceManager manager, CallbackInfo info) {
        MultipartTags.containers.get(this).load(manager);
        MultipartTags.setContainer(MultipartTags.containers.get(this));
    }

    @Inject(method = "toPacket", at = @At("RETURN"))
    private void onToPacket(PacketByteBuf buf, CallbackInfo info) {
        MultipartTags.containers.get(this).toPacket(buf);
    }

    @Inject(method = "fromPacket", at = @At("RETURN"))
    private static void onFromPacket(PacketByteBuf buf, CallbackInfoReturnable<TagManager> info) {
        MultipartTags.containers.get(info.getReturnValue()).fromPacket(buf);
    }
}
