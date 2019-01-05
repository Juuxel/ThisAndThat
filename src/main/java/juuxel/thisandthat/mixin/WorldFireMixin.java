package juuxel.thisandthat.mixin;

import juuxel.thisandthat.lib.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public abstract class WorldFireMixin implements ViewableWorld {
    @Redirect(method = "doesAreaContainFireSource", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private Block getBlockProxy(BlockState state) {
        if (state.getBlock().matches(ModTags.INSTANCE.getFire()))
            return Blocks.FIRE;

        return state.getBlock();
    }
}
