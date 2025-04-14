package net.i_no_am.freecamera.utils;

import com.mojang.authlib.GameProfile;
import net.i_no_am.freecamera.client.Global;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class FreeCameraEntity extends OtherClientPlayerEntity implements Global {

    private static FreeCameraEntity INSTANCE;

    private FreeCameraEntity(float health) {
        super(mc.world, new GameProfile(mc.player.getUuid(), mc.player.getName().getString()));

        copyPositionAndRotation(mc.player);
        lastYaw = getYaw();
        lastPitch = getPitch();
        headYaw = mc.player.headYaw;
        lastHeadYaw = headYaw;
        bodyYaw = mc.player.bodyYaw;
        lastBodyYaw = bodyYaw;

        Byte playerModel = mc.player.getDataTracker().get(PlayerEntity.PLAYER_MODEL_PARTS);
        dataTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);

        getAttributes().setFrom(mc.player.getAttributes());
        setPose(mc.player.getPose());

        capeX = getX();
        capeY = getY();
        capeZ = getZ();

        if (health <= 20) {
            setHealth(health);
        } else {
            setHealth(health);
            setAbsorptionAmount(health - 20);
        }
        getInventory().clone(mc.player.getInventory());
    }

    public void spawn() {
        unsetRemoved();
        mc.world.addEntity(this);
        setNoGravity(true);
    }

    public void despawn() {
        mc.world.removeEntity(getId(), RemovalReason.DISCARDED);
        setRemoved(RemovalReason.DISCARDED);
        INSTANCE = null;
    }

    public static FreeCameraEntity create(float health) {
        INSTANCE = new FreeCameraEntity(health);
        return INSTANCE;
    }
}