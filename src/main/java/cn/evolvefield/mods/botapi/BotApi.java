package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.config.ModConfig;
import cn.evolvefield.mods.botapi.init.handler.BotEventHandler;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import cn.evolvefield.onebot.sdk.connection.ModWebSocketClient;
import cn.evolvefield.onebot.sdk.core.Bot;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;

@Mod(Static.MODID)
public class BotApi {

    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static Path CONFIG_FOLDER ;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ModWebSocketClient service;
    public static EventDispatchers dispatchers;
    public static Bot bot;
    public static ModConfig config ;

    public BotApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);

    }
    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        SERVER = event.getServer();
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) throws Exception{
        config = ConfigHandler.load();//读取配置
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (config.getCommon().isAutoOpen()) {
            try {
                service = ConnectFactory.createWebsocketClient(config.getBotConfig(), blockingQueue);
                service.create();//创建websocket连接
                bot = service.createBot();//创建机器人实例
            } catch (Exception e) {
                Static.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        CustomCmdHandler.getInstance().load();//自定义命令加载
        BotEventHandler.init(dispatchers);//事件监听
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event){
        CustomCmdHandler.getInstance().clear();
        if (dispatchers != null) {
            dispatchers.stop();
        }
        if (service != null) {
            config.getBotConfig().setReconnect(false);
            service.close();
        }
        ConfigHandler.save(config);
        Static.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");

    }

}
