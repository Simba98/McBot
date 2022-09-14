package cn.evolvefield.mods.botapi.core;

import cn.evolvefield.mods.botapi.handler.ActionHandler;

import javax.websocket.Session;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 18:53
 * Version: 1.0
 */
public class BotFactory {
    private ActionHandler actionHandler;

    /**
     * 创建Bot对象
     *
     * @param selfId  机器人账号
     * @param session {@link Session}
     * @return {@link Bot}
     */
    public Bot createBot(long selfId, Session session) {
        return new Bot(selfId, session, actionHandler);
    }
}
