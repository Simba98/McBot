package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.platform.Services;
import cn.evolvefield.mods.botapi.util.FileUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class CommonClass {

    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;

    public static void init() {

        Constants.chatImageOn = Services.PLATFORM.isModLoaded("chatimage");
        CONFIG_FOLDER = Services.PLATFORM.getConfigDir().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Constants.MOD_ID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);
    }
}
