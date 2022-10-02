package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.impl.init.config.ModConfig;
import cn.evolvefield.mods.botapi.impl.init.handler.BotEventHandler;
import cn.evolvefield.mods.botapi.impl.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.impl.init.handler.CustomCmdHandler;
import cn.evolvefield.mods.botapi.sdk.connection.ConnectFactory;
import cn.evolvefield.mods.botapi.sdk.connection.ModWebSocketClient;
import cn.evolvefield.mods.botapi.sdk.core.Bot;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.util.FileUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
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
    private static LinkedBlockingQueue<String> blockingQueue;
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
        service = ConnectFactory.createWebsocketClient(config.getBotConfig(), blockingQueue);
        service.create();//创建websocket连接
        bot = service.createBot();//创建机器人实例
        dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        CustomCmdHandler.getInstance().load();//自定义命令加载
        BotEventHandler.init(dispatchers);//事件监听

    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event){
        dispatchers.stop();
        CustomCmdHandler.getInstance().clear();
        ConfigHandler.save(config);
        if (service != null) {
            service.close();
        }
        Static.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
    }

}
