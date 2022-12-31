package cn.evolvefield.mods.botapi.events;

import cn.evolvefield.mods.botapi.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 14:02
 * Description:
 */
public class TickEvents {
    private static final Queue<String> toSendQueue = new LinkedList<>();

    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }


    public static void init(MinecraftServer server) {
        String toSend = toSendQueue.poll();
        if (Constants.config != null
                && toSend != null
        ) {

            Component textComponents = Component.literal(toSend);
            server.getPlayerList().broadcastSystemMessage(textComponents, true);
        }

    }

}
