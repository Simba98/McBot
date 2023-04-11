package cn.evolvefield.mods.botapi.init;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.core.entity.UserMessage;
import cn.evolvefield.mods.botapi.core.web.WebSocketServer;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.services.Services;
import cn.evolvefield.mods.botapi.util.FileUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ModCommon {
    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static ExecutorService serverService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotServer %d").setDaemon(true).build());
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;

    private static final WebSocketServer ws = new WebSocketServer();

    private static final MutableComponent reply = Component.literal("点击回复");

    public static Thread main;
    public static void init() {
        Constants.chatImageOn = Services.PLATFORM.isModLoaded("chatimage");
        CONFIG_FOLDER = Services.PLATFORM.getConfigPath().resolve("botapi");
        FileUtil.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Constants.MOD_ID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);
        // 设置点击回复样式和点击事件
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "回复 ");
        reply.setStyle(Style.EMPTY
                .withColor(ChatFormatting.AQUA)
                .withClickEvent(clickEvent)
                .withItalic(true));
    }

    /**
     * 向所有玩家发送消息
     * @param msg 消息
     */
    public static void sendMessageToAllPlayers(UserMessage msg){
        // 拼接要发送对消息
        MutableComponent text = Component.literal (ChatFormatting.DARK_GREEN + "[")
                .append(ChatFormatting.AQUA + msg.getPlatform())
                .append(ChatFormatting.WHITE + "(")
                .append(ChatFormatting.DARK_PURPLE + msg.getId())
                .append(ChatFormatting.WHITE + ")")
                .append(ChatFormatting.DARK_GREEN + "]")
                .append(ChatFormatting.GOLD + " <" + msg.getName() + "> ")
                .append(ChatFormatting.WHITE + msg.getMessage() + " ")
                .append(reply);
        // 对所有玩家发送消息
        Services.PLATFORM.getServer().getPlayerList().broadcastSystemMessage(text,false);
    }

    public static void create(MinecraftServer server){
       main = new Thread(() -> ws.create(ConfigHandler.cached(), server), "WebSocketServer");
       main.start();
    }

    public static void stop(){
        ws.stop();
    }

    public static void broadcast(UserMessage msg){
        ws.broadcast(msg);
    }



}
