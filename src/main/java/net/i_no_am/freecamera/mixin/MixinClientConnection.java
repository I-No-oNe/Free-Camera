package net.i_no_am.freecamera.mixin;

import net.i_no_am.freecamera.FreeCamera;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    private void sendPacket(Packet<?> packet, CallbackInfo ci) {
        if (FreeCamera.isCameraActive() && packet instanceof PlayerMoveC2SPacket) {
            ci.cancel();
        }
    }

}
