package cn.evolvefield.mods.botapi;

import net.minecraft.util.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Constants {

    public static final String MOD_ID = "botapi";
    public static final String MOD_NAME = "BotConnect";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();
    public static final RandomSource RANDOM_SOURCE = RandomSource.create();

    public static boolean chatImageOn;
    public static boolean isShutdown = false;
}
