package cn.evolvefield.mods.botapi.init.handler;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.Queue;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickEventHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }

    @SubscribeEvent
    public static void onTickEvent(TickEvent.LevelTickEvent event) {
        var server = event.level.getServer();
        String toSend = toSendQueue.poll();
        if (server != null
                && ConfigHandler.cached() != null
                && server.isDedicatedServer()
                && toSend != null) {
            Component textComponents = Component.literal(toSend);
            server.getPlayerList().broadcastSystemMessage(textComponents, false);
        }
    }
}
