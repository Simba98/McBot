package cn.evolvefield.mods.botapi.platform.services;

import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);


    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    MinecraftServer getServer();

    Path getConfigDir();
}
