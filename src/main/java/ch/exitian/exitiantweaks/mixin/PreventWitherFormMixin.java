package ch.exitian.exitiantweaks.mixin;

import ch.exitian.exitiantweaks.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.WitherSkullBlock.checkSpawn;


@Mixin(WitherSkullBlock.class)
public class PreventWitherFormMixin {

    @Inject(method = "checkSpawn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"), cancellable = true)
    private static void checkSpawnMixin(Level pLevel, BlockPos pPos, CallbackInfo ci) {
        if (Config.preventWitherBossForm) {
            ci.cancel();
        } else if (pLevel.getBlockEntity(pPos) instanceof SkullBlockEntity skullblockentity) {
            checkSpawn(pLevel, pPos, skullblockentity);
        }
    }

}
