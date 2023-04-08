package cn.evolvefield.mods.botapi.handler;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.chat.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class TickEventHandler {
    private static final Queue<String> msgReceive = new LinkedList<>();
    ;

    public static Queue<String> getMsgReceive() {
        return msgReceive;
    }


    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            String toSend = msgReceive.poll();
            if (server.isDedicatedServer()
                    && toSend != null
            ) {
                Component textComponents = Component.literal(toSend);
                server.getPlayerList().broadcastSystemMessage(textComponents, false);
            }
        });
    }
}
