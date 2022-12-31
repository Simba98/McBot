package cn.evolvefield.mods.botapi.handler;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.cmd.custom.CustomCmd;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 13:47
 * Description:
 */
public class CustomCmdHandler {
    private static final CustomCmdHandler INSTANCE = new CustomCmdHandler();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private static final File dir = Constants.CONFIG_FOLDER.resolve("custom_cmd").toFile();


    private final Map<String, CustomCmd> customCmdMap = new LinkedHashMap<>();

    public static CustomCmdHandler getInstance() {
        return INSTANCE;
    }

    public List<CustomCmd> getCustomCmds() {
        return Lists.newArrayList(this.customCmdMap.values());
    }

    public Map<String, CustomCmd> getCustomCmdMap() {
        return customCmdMap;
    }

    public CustomCmd getCustomCmdByAlies(String alies) {
        return this.customCmdMap.get(alies);
    }

    public void load() {
        var stopwatch = Stopwatch.createStarted();

        this.writeDefault();

        clear();

        if (!dir.mkdirs() && dir.isDirectory()) {
            this.loadFiles(dir);
        }

        stopwatch.stop();

        Constants.LOGGER.info("Loaded {} custom cmd(s) in {} ms", this.customCmdMap.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public void writeDefault() {
        if (!dir.exists() && dir.mkdirs()) {
            var json = new JsonObject();
            json.addProperty("alies", "list");
            json.addProperty("content", "list");
            json.addProperty("role", 0);
            json.addProperty("enable", true);

            FileWriter writer = null;

            try {
                var file = new File(dir, "list.json");
                writer = new FileWriter(file);

                GSON.toJson(json, writer);
                writer.close();
            } catch (Exception e) {
                Constants.LOGGER.error("An error occurred while generating default custom cmd", e);
            } finally {
                IOUtils.closeQuietly(writer);
            }

        }
    }

    private void loadFiles(File dir) {
        var files = dir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (files == null)
            return;

        for (var file : files) {
            JsonObject json;
            InputStreamReader reader = null;
            CustomCmd customCmd = null;

            try {
                reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                var name = file.getName().replace(".json", "");
                json = JsonParser.parseReader(reader).getAsJsonObject();

                customCmd = CustomCmd.loadFromJson(new ResourceLocation(Constants.MOD_ID, name), json);

                reader.close();
            } catch (Exception e) {
                Constants.LOGGER.error("An error occurred while loading custom cmd", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            if (customCmd != null && customCmd.isEnabled()) {
                var alies = customCmd.getCmdAlies();

                this.customCmdMap.put(alies, customCmd);
            }
        }
    }

    public void clear() {
        this.customCmdMap.clear();
    }
}
