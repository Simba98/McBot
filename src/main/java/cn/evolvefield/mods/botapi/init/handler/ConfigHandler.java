package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.config.ModConfig;
import cn.evolvefield.onebot.sdk.util.json.util.JSONFormat;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class ConfigHandler {
    private static final Gson GSON = new Gson();

    public static ModConfig load() {
        ModConfig config = new ModConfig();

        if (!BotApi.CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(BotApi.CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path configPath = BotApi.CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        if (configPath.toFile().isFile()) {
            try {
                config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        ModConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    public static void save(ModConfig config) {
        if (config != null) {
            if (!BotApi.CONFIG_FOLDER.toFile().isDirectory()) {
                try {
                    Files.createDirectories(BotApi.CONFIG_FOLDER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Path configPath = BotApi.CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
            try {
                FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void onChange() {
        ConfigHandler.save(BotApi.config);
        BotApi.config = ConfigHandler.load();
    }
}
