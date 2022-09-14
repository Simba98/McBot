package cn.evolvefield.mods.botapi.listener.impl;

import cn.evolvefield.mods.botapi.listener.DefaultHandlerListener;
import cn.evolvefield.mods.botapi.listener.Handler;
import cn.evolvefield.mods.botapi.model.event.message.GroupMessage;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 16:15
 * Version: 1.0
 */
public class GroupMessageListener extends DefaultHandlerListener<GroupMessage> {

    @Override
    public void onMessage(GroupMessage groupMessage) {
        //处理逻辑
        String message = groupMessage.getMessage();
        String[] split = message.split(" ");
        String key = split[0];
        Handler<GroupMessage> handler = getHandler(key);
        if (handler != null) {
        handler.handle(groupMessage);
        }
    }
}
