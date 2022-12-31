package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.ChatEvents;
import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:37
 * Version: 1.0
 */
public class ChatEventHandler {
    public static void init() {
        ServerLevelEvents.Server_Chat.register((player, message, component) -> {
            ChatEvents.init(player, message);
        });
    }
}
