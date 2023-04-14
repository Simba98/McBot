package cn.evolvefield.mods.botapi.init.handler;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
    public static void onTickEvent(TickEvent.WorldTickEvent event) {
        var server = event.world.getServer();
        String toSend = toSendQueue.poll();
        if (server != null
                && ConfigHandler.cached() != null
                && server.isDedicatedServer()
                && toSend != null) {
            Component textComponents = new TextComponent(toSend);
            server.getPlayerList().broadcastMessage(textComponents, ChatType.SYSTEM, Util.NIL_UUID);
        }
    }
}
