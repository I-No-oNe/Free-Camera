package net.i_no_am.freecamera.utils;

import com.mojang.authlib.GameProfile;
import net.i_no_am.freecamera.client.Global;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

import java.util.Objects;
import java.util.UUID;

public class FakePlayerUtils extends OtherClientPlayerEntity implements Global {

    private final ClientPlayerEntity p = mc.player;
    private final ClientWorld w = mc.world;

    public FakePlayerUtils() {
        super(Objects.requireNonNull(mc.world), new GameProfile(UUID.fromString("66123666-6666-6666-6666-666666666600"), mc.player.getName().getString()));
        copyPositionAndRotation(mc.player);
        copyInventory();
        copyPlayerModel();
        copyRotation();
        resetCapeMovement();
        spawn();
    }

    private void copyInventory() {
        getInventory().clone(p.getInventory());
    }

    private void copyPlayerModel() {
        dataTracker.set(PLAYER_MODEL_PARTS, mc.player.getDataTracker().get(PLAYER_MODEL_PARTS));
    }

    private void copyRotation() {
        headYaw = p.headYaw;
        bodyYaw = p.bodyYaw;
    }

    private void resetCapeMovement() {
        capeX = getX();
        capeY = getY();
        capeZ = getZ();
    }

    private void spawn() {
        w.addEntity(this);
    }

    public void despawn() {
        p.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
        discard();
    }
}
