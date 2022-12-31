package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.TickEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class TickEventHandler {

    @SubscribeEvent
    public static void onTickEvent(TickEvent.ServerTickEvent event) {
        TickEvents.init(event.getServer());
    }
}
