package net.i_no_am.freecamera.mixin;

import com.mojang.authlib.GameProfile;
import net.i_no_am.freecamera.client.Global;
import net.i_no_am.freecamera.utils.ConfigUtils;
import net.i_no_am.freecamera.utils.FakePlayerUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayerEntity extends PlayerEntity implements Global {

    public MixinAbstractClientPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> cir) {
        if (mc.player == null) return;
        if ((Object) this instanceof FakePlayerUtils) return;
        if (ConfigUtils.isCameraActive()) {
            cir.setReturnValue(true);
        }
    }
}
