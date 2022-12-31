package cn.evolvefield.mods.botapi;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Constants.MOD_ID)
public class ForgeBotApi {

    public ForgeBotApi() {

        CommonBotApi.fileInit(FMLPaths.CONFIGDIR.get());
        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        CommonBotApi.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        CommonBotApi.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        CommonBotApi.onServerStopping(event.getServer());
    }
}
