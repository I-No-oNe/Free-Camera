package net.i_no_am.freecamera.mixin;

import net.i_no_am.freecamera.client.Global;
import net.i_no_am.freecamera.utils.ConfigUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends Entity implements Global {

    protected MixinPlayerEntity(World world) {
        super(mc.player.getType(), world);
    }
    @Inject(method = "isPushedByFluids", at = @At("HEAD"), cancellable = true)
    private void isPushedByFluids(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigUtils.isCameraActive()) {
            cir.setReturnValue(false);
        }
    }
}
