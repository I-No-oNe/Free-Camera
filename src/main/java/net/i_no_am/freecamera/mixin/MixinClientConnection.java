package net.i_no_am.freecamera.mixin;

import net.i_no_am.freecamera.utils.ConfigUtils;
import net.i_no_am.freecamera.utils.PlayerUtils;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (PlayerUtils.notNull() && ConfigUtils.isCameraActive() && packet instanceof PlayerMoveC2SPacket) {
            ci.cancel();
        }
    }
}