package net.i_no_am.freecamera.utils;

import net.i_no_am.freecamera.client.Global;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class PlayerUtils implements Global {

    private static DimensionType previousDimension;


    private static ClientPlayerEntity getPlayer() {
        return mc.player;
    }

    public static boolean notNull() {
        return getPlayer() != null;
    }

    public static boolean isDied() {
        ClientPlayerEntity p = getPlayer();
        return p != null && !p.isAlive();
    }

    public static boolean changedDimension() {
        ClientPlayerEntity p = getPlayer();
        if (p != null && previousDimension != null) {
            DimensionType currentDimension = p.getWorld().getDimension();
            boolean hasChanged = !currentDimension.equals(previousDimension);
            previousDimension = currentDimension;
            return hasChanged;
        }
        if (p != null) {
            previousDimension = p.getWorld().getDimension();
        }
        return false;
    }


    public static void setFlying(boolean val) {
        ClientPlayerEntity p = getPlayer();
        if (p != null) {
            p.getAbilities().flying = val;
        }
    }

    public static void setVec3d(Vec3d vec3d) {
        ClientPlayerEntity p = getPlayer();
        if (p != null) {
            p.setVelocity(vec3d);
        }
    }

    public static boolean canUseFreeCam(){
        return isOnGround() || isInFluid() || isFlyingWithElytra();
    }

    public static boolean isOnGround() {
        ClientPlayerEntity p = getPlayer();
        return p != null && ConfigUtils.isCameraActive() || p.isOnGround() && p != null;
        }
    public static boolean isInFluid() {
        ClientPlayerEntity p = getPlayer();
        return p != null && ConfigUtils.isCameraActive() || p.isInFluid() && p != null;
        }
    public static boolean isFlyingWithElytra(){
        ClientPlayerEntity p = getPlayer();
        return p != null && ConfigUtils.isCameraActive() || p.getInventory().getArmorStack(2).equals(Items.ELYTRA) && p != null;
    }
}

