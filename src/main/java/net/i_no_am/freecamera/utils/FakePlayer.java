package net.i_no_am.freecamera.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class FakePlayer extends AbstractClientPlayerEntity {

    // inspired by https://github.com/Wurst-Imperium/Wurst7/blob/master/src/main/java/net/wurstclient/util/FakePlayerEntity.java

    private final ClientPlayerEntity player;
    private final ClientWorld world;

    public FakePlayer(ClientPlayerEntity player, ClientWorld world) {
        super(world, new GameProfile(UUID.randomUUID(), player.getName().getString()));

        this.player = player;
        this.world = world;

        copyPositionAndRotation(player);

        copyInventory();
        copyPlayerModel(player, this);
        copyRotation();
        resetCapeMovement();

        spawn();
    }

    private void copyInventory() {
        getInventory().clone(player.getInventory());
    }

    private void copyPlayerModel(Entity from, Entity to) {
        DataTracker fromTracker = from.getDataTracker();
        DataTracker toTracker = to.getDataTracker();
        Byte playerModel = fromTracker.get(PlayerEntity.PLAYER_MODEL_PARTS);
        toTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, playerModel);
    }

    private void copyRotation() {
        headYaw = player.headYaw;
        bodyYaw = player.bodyYaw;
    }

    private void resetCapeMovement() {
        capeX = getX();
        capeY = getY();
        capeZ = getZ();
    }

    private void spawn() {
        world.addEntity(this);
    }

    public void despawn() {
        discard();
    }

    public void resetPlayerPosition() {
        player.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
    }
}