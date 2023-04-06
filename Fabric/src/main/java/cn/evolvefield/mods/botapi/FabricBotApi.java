package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.web.connect.ConnectionFactory;
import cn.evolvefield.mods.botapi.web.connect.WSServer;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.handler.KoishiHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.LinkedBlockingQueue;


public class FabricBotApi implements ModInitializer {
    public static MinecraftServer SERVER = null;
    public static KoishiHandler fromKoishi = null;
    public static LinkedBlockingQueue<String> receive;

    public static WSServer ws;

    @Override
    public void onInitialize() {

        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);

    }

    private void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }

    private void onServerStarted(MinecraftServer server) {
        ws = new ConnectionFactory(ConfigHandler.cached()).ws;
        ws.start();
        receive = new LinkedBlockingQueue<>();//使用队列接收从koishi来的数据
        fromKoishi = new KoishiHandler(receive);

    }

    private void onServerStopped(MinecraftServer server) {
        try {
            ConfigHandler.save();//保存配置
            Constants.LOG.info("▌ §c正在关闭群服互联 §a┈━═☆");
            //CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
            Constants.isShutdown = true;
            ConfigHandler.watcher.get().close();//配置监控关闭
            CommonClass.configWatcherExecutorService.shutdownNow();//监控进程关闭
            ws.stop();//ws客户端关闭
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
