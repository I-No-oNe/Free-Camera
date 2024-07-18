package net.i_no_am.freecamera;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.i_no_am.freecamera.client.Global;
import net.i_no_am.freecamera.utils.ConfigUtils;
import net.i_no_am.freecamera.utils.FakePlayerUtils;
import net.i_no_am.freecamera.utils.PlayerUtils;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FreeCamera implements ModInitializer, Global {


	public static boolean isCameraActive = false;
	public FakePlayerUtils fakePlayerUtils;


	public static final KeyBinding BIND = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"Toggle Free Camera",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_F4,
			"Free Camera"
	));


	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (BIND.wasPressed() && PlayerUtils.canUseFreeCam()) {
				ConfigUtils.toggleCamera();
				if (isCameraActive) {
					fakePlayerUtils = new FakePlayerUtils();
					PlayerUtils.setFlying(true);
				}else{
					PlayerUtils.setVec3d(Vec3d.ZERO);
					PlayerUtils.setFlying(false);
					if (fakePlayerUtils != null) {
						fakePlayerUtils.despawn();
					}
					fakePlayerUtils = null;
				}

			}
			if (PlayerUtils.isDied() || PlayerUtils.changedDimension()) {
				if (isCameraActive) {
					ConfigUtils.toggleCamera();
					if (fakePlayerUtils != null) {
						fakePlayerUtils.despawn();
					}
					fakePlayerUtils = null;
				}
			}
		});
	}
}