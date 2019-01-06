package juuxel.thisandthat.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.shadowfacts.simplemultipart.container.AbstractContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO Remove on SimpleMultipart update
@Mixin(value = AbstractContainerBlockEntity.class, remap = false)
public class MultipartContainerMixin extends BlockEntity {
    public MultipartContainerMixin(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    @Inject(method = "updateWorld", at = @At(value = "RETURN"))
    private void updateWorld(CallbackInfo info) {
        world.getBlockState(pos).updateNeighborStates(world, pos, 3);
    }
}
