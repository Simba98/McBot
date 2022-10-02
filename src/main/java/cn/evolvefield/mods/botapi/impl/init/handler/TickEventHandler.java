package cn.evolvefield.mods.botapi.impl.init.handler;

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
    public static void onTickEvent(TickEvent.LevelTickEvent event) {
        String toSend = toSendQueue.poll();
        if (!event.level.isClientSide && toSend != null) {
            Component textComponents = Component.literal(toSend);
            Objects.requireNonNull(event.level.getServer()).getPlayerList().broadcastSystemMessage(textComponents, true);
        }
    }
}
