package ch.exitian.exitiantweaks.mixin;

import ch.exitian.exitiantweaks.config.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerCanUseGameMasterBlockMixin extends Entity {

    @Shadow @Final private Abilities abilities;

    public PlayerCanUseGameMasterBlockMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "canUseGameMasterBlocks", at = @At("TAIL"), cancellable = true)
    private void canUseGameMasterBlocksMixin(CallbackInfoReturnable<Boolean> cir) {
         if ((this.abilities.instabuild && this.getPermissionLevel() >= 2) || Config.overrideCommandBlockPerm) {
             cir.setReturnValue(Config.allowCommandBlockUse);
         }
    }
}
