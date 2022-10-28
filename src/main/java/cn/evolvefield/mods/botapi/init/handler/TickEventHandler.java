package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class TickEventHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();
    ;

    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }


    public static void init() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            String toSend = toSendQueue.poll();
            if (BotApi.config != null
                    && !world.isClientSide
                    && toSend != null
            ) {
                Component textComponents = new TextComponent(toSend);
                world.getServer().getPlayerList().broadcastMessage(textComponents, ChatType.SYSTEM, Util.NIL_UUID);
            }
        });
    }
}
