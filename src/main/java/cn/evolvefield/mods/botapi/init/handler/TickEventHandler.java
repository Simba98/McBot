package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Mod.EventBusSubscriber
public class TickEventHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }

    @SubscribeEvent
    public static void onTickEvent(TickEvent.WorldTickEvent event) {
        String toSend = toSendQueue.poll();
        if (BotApi.config != null
                && !event.world.isClientSide
                && toSend != null
                && event.world.getServer() != null
        ) {
            Component textComponents = new TextComponent(toSend);
            event.world.getServer().getPlayerList().broadcastMessage(textComponents, ChatType.SYSTEM, Util.NIL_UUID);
        }
    }
}
