package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.core.web.connect.ConnectionFactory;
import cn.evolvefield.mods.botapi.core.web.connect.WSServer;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.ModCommon;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.LinkedBlockingQueue;

public class ModFabric implements ModInitializer {
    public static MinecraftServer SERVER = null;
    public static WSServer ws;
    @Override
    public void onInitialize() {

        ModCommon.init();
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
    }


    private void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }
    private void onServerStarted(MinecraftServer server) {
        ws = new ConnectionFactory(ConfigHandler.cached(), server).ws;
        ws.start();

    }

    private void onServerStopped(MinecraftServer server) {
        try {
            ConfigHandler.save();//保存配置
            Constants.LOG.info("▌ §c正在关闭群服互联 §a┈━═☆");
            //CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
            Constants.isShutdown = true;
            ConfigHandler.watcher.get().close();//配置监控关闭
            ModCommon.configWatcherExecutorService.shutdownNow();//监控进程关闭
            ws.stop();//ws客户端关闭
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
