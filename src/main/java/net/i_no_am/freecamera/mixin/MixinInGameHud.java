package net.i_no_am.freecamera.mixin;

import net.i_no_am.freecamera.client.Global;
import net.i_no_am.freecamera.utils.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;

@Mixin(InGameHud.class)
public class MixinInGameHud implements Global {

    @Inject(method = "renderOverlayMessage", at = @At(value = "HEAD"))
    private void onRenderOverlayMessage(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ConfigUtils.isCameraActive()) {

            MutableText msgP1 = Text.literal("Free Camera is ");
            MutableText msgP2 = Text.literal("ON").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)));

            MutableText fullMsg = msgP1.append(msgP2);

            int messageWidth = mc.textRenderer.getWidth(fullMsg);
            int x = (mc.getWindow().getScaledWidth() / 2) - (messageWidth / 2);
            int y = mc.getWindow().getScaledHeight() - 51;

            context.drawText(mc.textRenderer, fullMsg, x, y, 0xFFFFFF, true);
        }
    }
}