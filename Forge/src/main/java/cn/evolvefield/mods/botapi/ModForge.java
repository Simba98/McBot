package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.init.ModCommon;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(Constants.MOD_ID)
public class ModForge {
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();

    public ModForge() {

        ModCommon.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        SERVER = event.getServer();
    }

}
