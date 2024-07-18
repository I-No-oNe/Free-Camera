package net.i_no_am.freecamera.utils;

import net.i_no_am.freecamera.FreeCamera;

public class ConfigUtils {
    public static boolean isCameraActive() {
        return FreeCamera.isCameraActive;
    }
    public static void toggleCamera() {
        FreeCamera.isCameraActive = !FreeCamera.isCameraActive;
    }
}
