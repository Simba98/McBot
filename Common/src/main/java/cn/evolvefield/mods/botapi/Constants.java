package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.Constants.config.ModConfig;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Constants {

    public static final String MOD_ID = "botapi";
    public static final String MOD_NAME = "Bot Connect";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static MinecraftServer SERVER = null;
    public static Path CONFIG_FOLDER;
    public static ModConfig config;

}
