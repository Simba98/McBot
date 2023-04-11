package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.init.ModCommon;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ModFabric implements ModInitializer {
    public static MinecraftServer SERVER = null;
    @Override
    public void onInitialize() {
            ModCommon.init();
            ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
            ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
            ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
            ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
    }


    private void onServerStarting(MinecraftServer server) {
        SERVER = server;//获取服务器实例
    }
    private void onServerStarted(MinecraftServer server) {
        ModCommon.serverService.execute(() -> {
            ModCommon.create(server);
        });
    }

    private void onServerStopping(MinecraftServer server) {
        try {
            Constants.isShutdown = true;
            ConfigHandler.save();//保存配置
            //CustomCmdHandler.INSTANCE.clear();//自定义命令持久层清空
            ModCommon.stop();
            ModCommon.serverService.shutdownNow();
        } catch (Exception e) {
            Constants.LOG.error(e.getMessage());
        }
    }


    private void onServerStopped(MinecraftServer server) {
        try {
            ConfigHandler.watcher.get().close();//配置监控关闭
            ModCommon.configWatcherExecutorService.shutdownNow();//监控进程关闭
        } catch (Exception e) {
            Constants.LOG.error(e.getMessage());
        }

    }

}
