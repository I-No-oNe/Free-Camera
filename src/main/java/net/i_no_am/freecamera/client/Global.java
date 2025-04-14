package net.i_no_am.freecamera.client;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;

public interface Global {
    @Nullable MinecraftClient mc = MinecraftClient.getInstance();
}
