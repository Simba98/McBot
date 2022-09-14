package cn.evolvefield.mods.botapi.listener.impl;

import cn.evolvefield.mods.botapi.listener.DefaultHandlerListener;
import cn.evolvefield.mods.botapi.listener.Handler;
import cn.evolvefield.mods.botapi.model.event.message.PrivateMessage;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 16:29
 * Version: 1.0
 */
public class PrivateMessageListener  extends DefaultHandlerListener<PrivateMessage> {
    @Override
    public void onMessage(PrivateMessage privateMessage) {
        //处理逻辑
        String message = privateMessage.getMessage();
        String[] split = message.split(" ");
        String key = split[0];
        Handler<PrivateMessage> handler = getHandler(key);
        if (handler != null) {
            handler.handle(privateMessage);
        }
    }
}
