package net.i_no_am.freecamera.mixin;

import com.mojang.authlib.GameProfile;
import net.i_no_am.freecamera.FreeCamera;
import net.i_no_am.freecamera.client.Global;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.SetCameraEntityS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
abstract class MixinAbstractClientPlayerEntity extends PlayerEntity implements Global {

    public MixinAbstractClientPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getGameMode", at = @At("HEAD"), cancellable = true)
    private void onIsSpectator(CallbackInfoReturnable<GameMode> cir) {
        if (FreeCamera.Config.isActive()) {
            if ((Object)this != mc.player) return;
            cir.setReturnValue(GameMode.SPECTATOR);
        }
    }
}