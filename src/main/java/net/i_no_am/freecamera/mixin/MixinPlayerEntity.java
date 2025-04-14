package net.i_no_am.freecamera.mixin;

import net.i_no_am.freecamera.FreeCamera;
import net.i_no_am.freecamera.client.Global;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements Global {

    @Inject(method = "isPushedByFluids", at = @At("HEAD"), cancellable = true)
    private void isPushedByFluids(CallbackInfoReturnable<Boolean> cir) {
        if (FreeCamera.Config.isActive()) {
            cir.setReturnValue(false);
        }
    }
}
