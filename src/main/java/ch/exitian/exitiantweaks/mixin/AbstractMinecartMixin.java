package ch.exitian.exitiantweaks.mixin;

import ch.exitian.exitiantweaks.config.Config;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartMixin {

    @Inject(method = "getMaxSpeedWithRail", at = @At("HEAD"), cancellable = true)
    public void getMaxSpeedWithRail(CallbackInfoReturnable<Double> callback) {
        callback.setReturnValue(Config.minecartSpeed);
    }
}