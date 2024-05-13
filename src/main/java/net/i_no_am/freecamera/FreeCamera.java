package net.i_no_am.freecamera;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.i_no_am.freecamera.utils.FakePlayer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW	;

public class FreeCamera implements ModInitializer {

	public static final KeyBinding BIND = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"Toggle Free Camera",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_F4,
			"Free Camera"
	));

	public FakePlayer fakePlayer;
	private static boolean isCameraActive = false;

	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			final ClientPlayerEntity player = client.player;
			if (player == null) return;
			final ClientWorld world = player.clientWorld;
			if (BIND.wasPressed()) {
				toggleCamera();
				if (isCameraActive) {
					fakePlayer = new FakePlayer(player, world);
				} else {
					player.setVelocity(Vec3d.ZERO);
					player.getAbilities().flying = false;
					if (fakePlayer != null) {
						fakePlayer.resetPlayerPosition();
						fakePlayer.despawn();
					}
					fakePlayer = null;
				}
			}

			if (isCameraActive) {
				player.getAbilities().flying = true;
			}
            if (!player.isAlive() || player.clientWorld.getDimension() != player.getWorld().getDimension()) {
				if (isCameraActive) {
					toggleCamera();
					if (fakePlayer != null) {
						fakePlayer.resetPlayerPosition();
						fakePlayer.despawn();
					}
					fakePlayer = null;
				}
			}
		});
	}

	public static boolean isCameraActive() {
		return isCameraActive;
	}

	private static void toggleCamera() {
		isCameraActive = !isCameraActive;
	}
}
