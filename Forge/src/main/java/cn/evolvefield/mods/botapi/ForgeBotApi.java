package cn.evolvefield.mods.botapi;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(Constants.MOD_ID)
public class ForgeBotApi {

    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();

    public ForgeBotApi() {


        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        SERVER = event.getServer();
    }
}
