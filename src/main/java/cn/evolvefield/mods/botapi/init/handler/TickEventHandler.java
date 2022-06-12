package cn.evolvefield.mods.botapi.init.handler;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
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
        if (!event.world.isClientSide && toSend != null) {
            Component textComponents = Component.literal(toSend);
            Objects.requireNonNull(event.world.getServer()).getPlayerList().broadcastSystemMessage(textComponents, ChatType.SYSTEM);
        }
    }
}
