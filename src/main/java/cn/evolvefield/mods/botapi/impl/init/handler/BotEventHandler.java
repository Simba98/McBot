package cn.evolvefield.mods.botapi.impl.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.sdk.listener.Handler;
import cn.evolvefield.mods.botapi.sdk.listener.SimpleListener;
import cn.evolvefield.mods.botapi.sdk.listener.impl.GroupMessageListener;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 11:51
 * Version: 1.0
 */
public class BotEventHandler {
    public static void handlerQQChat(EventDispatchers dispatchers){
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (!event.getMessage().contains("[CQ:") && BotApi.config.getStatus().isR_CHAT_ENABLE()
                        && event.getUserId() != BotApi.config.getCommon().getBotId()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getSender().getNickname(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        });
    }



}
