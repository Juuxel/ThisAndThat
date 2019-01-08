/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import juuxel.thisandthat.util.TagEntryExtensions;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.loot.condition.LootCondition;
import net.minecraft.world.loot.entry.TagEntry;
import net.minecraft.world.loot.function.LootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagEntry.Serializer.class)
public class TagEntrySerializerMixin {
    private static final String JSON_RANDOM = "thisandthat:random";

    @Inject(method = "method_451", at = @At("RETURN"))
    private void onSerialize(JsonObject object, TagEntry entry, JsonSerializationContext context, CallbackInfo info) {
        object.addProperty(JSON_RANDOM, ((TagEntryExtensions) entry).isRandom());
    }

    @Inject(method = "method_450", at = @At("RETURN"))
    private void onDeserialize(JsonObject object, JsonDeserializationContext context, int int_1, int int_2, LootCondition[] conditions, LootFunction[] functions, CallbackInfoReturnable<TagEntry> info) {
        ((TagEntryExtensions) info.getReturnValue()).setRandom(JsonHelper.getBoolean(object, JSON_RANDOM, false));
    }

    // Provide a default in case expand is not found ;-)
    @Redirect(method = "method_450", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;getBoolean(Lcom/google/gson/JsonObject;Ljava/lang/String;)Z"))
    private boolean getBoolean(JsonObject object, String key) {
        if (key.equals("expand") && !object.has(key))
            return false;

        return JsonHelper.getBoolean(object, key);
    }
}
