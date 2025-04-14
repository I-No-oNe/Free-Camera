package net.i_no_am.freecamera.utils;

import net.i_no_am.freecamera.FreeCamera;
import net.i_no_am.freecamera.client.Global;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class PlayerUtils implements Global {

    private static DimensionType previousDimension;

    public static boolean nullCheck() {
        return (mc == null || mc.player == null || mc.world == null);
    }

    public static boolean changedDimension() {
        if (previousDimension != null) {
            DimensionType currentDimension = mc.player.getWorld().getDimension();
            boolean hasChanged = !currentDimension.equals(previousDimension);
            previousDimension = currentDimension;
            return hasChanged;
        }
        if (mc != null) {
            previousDimension = mc.player.getWorld().getDimension();
        }
        return false;
    }


    public static void setFlying(boolean val) {
        mc.player.getAbilities().flying = val;
    }

    public static void setVec3d(Vec3d vec3d) {
        mc.player.setVelocity(vec3d);
    }

    private static boolean playerHurt() {
        return mc.player.hurtTime > 0;
    }

    public static boolean canUseFreeCam(){
       return FreeCamera.Config.isActive() && checks();
    }

    private static boolean checks() {
        return !playerHurt() || mc.player.isOnGround() || mc.player.isInFluid() || mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
    }
}

