package cn.evolvefield.mods.botapi.impl.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.impl.api.cmd.CmdApi;
import cn.evolvefield.mods.botapi.impl.api.listener.GroupCmdListener;
import cn.evolvefield.mods.botapi.sdk.listener.SimpleListener;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 11:51
 * Version: 1.0
 */
public class BotEventHandler {
    public static void init(EventDispatchers dispatchers){

        handlerQQChat(dispatchers);
        handlerQQCmds(dispatchers);
        dispatchers.start(10);//线程组处理任务
    }

    private static void handlerQQChat(EventDispatchers dispatchers){
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && !event.getMessage().contains("[CQ:")
                        && BotApi.config.getStatus().isR_CHAT_ENABLE()
                        && event.getUserId() != BotApi.config.getCommon().getBotId()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getSender().getNickname(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        });
    }

    private static void handlerQQCmds(EventDispatchers dispatchers){
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (BotApi.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && BotApi.config.getStatus().isRECEIVE_ENABLED()
                        && event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {
                    CmdApi.invokeCommandMain(event);
                }
            }
        });
    }



}
