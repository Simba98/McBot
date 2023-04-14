package cn.evolvefield.mods.botapi.init.handler;

import lombok.var;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
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
            var textComponents = new StringTextComponent(toSend);
            server.getPlayerList().broadcastMessage(textComponents, ChatType.SYSTEM, Util.NIL_UUID);
        }
    }
}
