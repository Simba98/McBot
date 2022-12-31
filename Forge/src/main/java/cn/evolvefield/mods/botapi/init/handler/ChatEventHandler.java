package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.ChatEvents;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        ChatEvents.init(event.getPlayer(), event.getRawText());
    }
}
