/* This file is a part of the This & That project
 * by Juuxel, licensed under the MIT license.
 * Full code and license: https://github.com/Juuxel/ThisAndThat
 */
package juuxel.thisandthat.mixin;

import juuxel.thisandthat.util.TTVoxelShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ViewableWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TorchBlock.class)
public class TorchPlacementMixin {
    private static final BoundingBox TOP_BOX = Block.createCuboidShape(6, 15.5, 6, 10, 16, 10).getBoundingBox();

    @Inject(method = "canPlaceAt", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onCanPlaceAt(BlockState state, ViewableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info,
                              BlockPos downPos, BlockState downState, Block downBlock) {
        if (!info.getReturnValue() && !"minecraft".equals(Registry.BLOCK.getId(downBlock).getNamespace())) {
            info.setReturnValue(TTVoxelShapes.containedIn(
                downState.getCollisionShape(world, downPos), TOP_BOX
            ));
        }

        // TODO Is this needed?
        /*if (!ModConfig.getInstance().modules.multiparts) return;

        try {
            BlockEntity entity = world.getBlockEntity(downPos);
            if (entity instanceof MultipartContainer) {
                if (((MultipartContainer) entity).getParts().stream().anyMatch(p ->
                        p.getMultipart() instanceof ModMultipart && ((ModMultipart) p.getMultipart()).canSupportTorches(
                                p.getState(), world, downPos
                        ))) {
                    info.setReturnValue(true);
                }
            }
        } catch (NullPointerException e) {
            LogManager.getLogger().debug("[ThisAndThat] NPE during torch placement");
        }*/
    }
}
