package net.i_no_am.freecamera;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.i_no_am.freecamera.client.Global;
import net.i_no_am.freecamera.utils.FreeCameraEntity;
import net.i_no_am.freecamera.utils.PlayerUtils;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FreeCamera implements ClientModInitializer, Global {

    public static final KeyBinding BIND = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle Free Camera", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4, KeyBinding.GAMEPLAY_CATEGORY));

    public static FreeCameraEntity fakePlayer;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (PlayerUtils.nullCheck()) return;

            // Toggle key pressed
            if (BIND.wasPressed()) {
                if (!Config.isActive() && PlayerUtils.canUseFreeCam()) {
                    FreeCameraManager.onEnable();
                } else {
                    FreeCameraManager.onDisable();
                }
            }

            // Auto-disable if dead or dimension changed
            if (Config.isActive() && (!mc.player.isAlive() || PlayerUtils.changedDimension())) {
                FreeCameraManager.onDisable();
            }
        });
    }

    public static class FreeCameraManager {

        public static void onEnable() {
            mc.execute(() -> {
                fakePlayer = FreeCameraEntity.create(20F);
                if (fakePlayer != null) {
                    fakePlayer.spawn();
                    PlayerUtils.setFlying(true);
                    Config.toggle();
                }
            });
        }

        public static void onDisable() {
            if (fakePlayer != null && fakePlayer.isAlive()) {
                mc.player.updatePosition(fakePlayer.getX(), fakePlayer.getY(), fakePlayer.getZ());
                fakePlayer.despawn();
            }
            PlayerUtils.setVec3d(Vec3d.ZERO);
            PlayerUtils.setFlying(false);
            Config.toggle();
        }
    }

    public static class Config {

        private static boolean active = false;

        public static boolean isActive() {
            return active;
        }

        public static void toggle() {
            active = !active;
        }
    }
}
