package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.events.BotEvents;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import cn.evolvefield.onebot.sdk.connection.ModWebSocketClient;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

import static cn.evolvefield.mods.botapi.Constants.CONFIG_FOLDER;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonBotApi {

    public static LinkedBlockingQueue<String> blockingQueue;
    public static ModWebSocketClient service;
    public static EventDispatchers dispatchers;
    public static Bot bot;

    public static void fileInit(Path config) {
        CONFIG_FOLDER = config.resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);

    }

    static void onServerStarting(MinecraftServer server) {
        Constants.SERVER = server;//获取服务器实例
    }

    static MinecraftServer getServer() {
        return Constants.SERVER;
    }

    static void onServerStarted(MinecraftServer server) {
        Constants.config = ConfigHandler.load();//读取配置
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (Constants.config.getCommon().isAutoOpen()) {
            try {
                service = ConnectFactory.createWebsocketClient(Constants.config.getBotConfig(), blockingQueue);
                service.create();//创建websocket连接
                bot = service.createBot();//创建机器人实例
            } catch (Exception e) {
                Constants.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        CustomCmdHandler.getInstance().load();//自定义命令加载
        BotEvents.init(dispatchers);//事件监听
    }

    static void onServerStopping(MinecraftServer server) {
        Constants.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
        CustomCmdHandler.getInstance().clear();
        if (dispatchers != null) {
            dispatchers.stop();
        }
        if (service != null) {
            Constants.config.getBotConfig().setReconnect(false);
            service.setReconnect(false);
            service = null;
        }
        ConfigHandler.save(Constants.config);


    }
}
