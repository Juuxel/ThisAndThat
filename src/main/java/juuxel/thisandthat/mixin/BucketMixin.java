/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import juuxel.thisandthat.block.TankBlock;
import juuxel.watereddown.api.FluidProperty;
import juuxel.watereddown.api.WDProperties;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.HitResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketMixin extends Item {
    @Shadow @Final
    private Fluid fluid;

    public BucketMixin(Settings var1) {
        super(var1);
    }

    @Shadow
    private ItemStack getFilledStack(ItemStack bucketStack, PlayerEntity player, Item filledBucket) { return null; }

    @Shadow
    protected abstract ItemStack getEmptiedStack(ItemStack stack, PlayerEntity entity);

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    private void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        boolean isEmpty = fluid == Fluids.EMPTY;
        ItemStack stack = player.getStackInHand(hand);
        HitResult result = getHitResult(world, player, isEmpty);
        if (result == null) return;

        if (result.type == HitResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof TankBlock) {
                if (isEmpty && state.get(WDProperties.FLUID).getFluid() != Fluids.EMPTY) {
                    if (!world.isClient)
                        world.setBlockState(pos, world.getBlockState(pos).with(WDProperties.FLUID, FluidProperty.EMPTY));
                    ItemStack filled = getFilledStack(stack, player, fluid.getBucketItem());
                    info.setReturnValue(new TypedActionResult<>(ActionResult.SUCCESS, filled));
                } else if (state.get(WDProperties.FLUID).getFluid() == Fluids.EMPTY) {
                    if (!world.isClient)
                        world.setBlockState(pos, world.getBlockState(pos).with(WDProperties.FLUID, new FluidProperty.Wrapper(fluid)));
                    ItemStack emptied = getEmptiedStack(stack, player);
                    info.setReturnValue(new TypedActionResult<>(ActionResult.SUCCESS, emptied));
                }
            }
        }
    }
}
