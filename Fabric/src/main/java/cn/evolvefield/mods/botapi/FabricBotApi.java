package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.handler.ChatEventHandler;
import cn.evolvefield.mods.botapi.init.handler.CmdEventHandler;
import cn.evolvefield.mods.botapi.init.handler.PlayerEventHandler;
import cn.evolvefield.mods.botapi.init.handler.TickEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

public class FabricBotApi implements ModInitializer {

    @Override
    public void onInitialize() {

        CommonBotApi.fileInit(FabricLoader.getInstance().getConfigDir());

        ServerLifecycleEvents.SERVER_STARTING.register(CommonBotApi::onServerStarting);

        ServerLifecycleEvents.SERVER_STARTED.register(CommonBotApi::onServerStarted);

        ServerLifecycleEvents.SERVER_STOPPING.register(CommonBotApi::onServerStopping);
        CmdEventHandler.init();
        PlayerEventHandler.init();
        ChatEventHandler.init();
        TickEventHandler.init();
    }
}
