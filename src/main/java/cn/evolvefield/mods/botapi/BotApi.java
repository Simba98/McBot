package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.handler.BotEventHandler;
import cn.evolvefield.mods.botapi.init.handler.CmdEventHandler;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import cn.evolvefield.onebot.client.connection.ConnectFactory;
import cn.evolvefield.onebot.client.core.Bot;
import cn.evolvefield.onebot.client.handler.EventBus;
import cn.evolvefield.onebot.sdk.util.FileUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
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

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

@Mod(Const.MODID)
public class BotApi {

    public static ScheduledExecutorService configWatcherExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("BotApi Config Watcher %d").setDaemon(true).build());
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static Path CONFIG_FOLDER;
    public static File CONFIG_FILE;
    public static LinkedBlockingQueue<String> blockingQueue;
    public static ConnectFactory service;
    public static EventBus dispatchers;
    public static Bot bot;
    public static Thread app;
    boolean init = false;
    public BotApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve("botapi");
        FileUtils.checkFolder(CONFIG_FOLDER);
        CONFIG_FILE = CONFIG_FOLDER.resolve(Const.MODID + ".json").toFile();
        ConfigHandler.init(CONFIG_FILE);

    }

    @SubscribeEvent
    public void cmdRegister(RegisterCommandsEvent event) {
        if (init) CmdEventHandler.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        SERVER = event.getServer();
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) throws Exception{
        if (!init) {
            CmdEventHandler.register(event.getServer().getCommands().getDispatcher());
            init = true;
        }
        blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        if (ConfigHandler.cached().getCommon().isAutoOpen()) {
            try {
                app = new Thread(() -> {
                    service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), blockingQueue);//创建websocket连接
                    bot = service.ws.createBot();//创建机器人实例
                }, "BotServer");
                app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端未配置或未打开");
            }
        }
        dispatchers = new EventBus(blockingQueue);//创建事件分发器
        CustomCmdHandler.INSTANCE.load();//自定义命令加载
        BotEventHandler.init(dispatchers);//事件监听s
    }
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event){
        Const.isShutdown = true;
        Const.LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
        dispatchers.stop();//分发器关闭
        service.stop();
        app.interrupt();

    }
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event){
        try {
            ConfigHandler.save();//保存配置
            CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
            ConfigHandler.watcher.get().close();//配置监控关闭
            BotApi.configWatcherExecutorService.shutdownNow();//监控进程关闭
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
