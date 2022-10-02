package cn.evolvefield.mods.botapi.sdk.connection;

import cn.evolvefield.mods.botapi.sdk.config.BotConfig;
import cn.evolvefield.mods.botapi.sdk.handler.ActionHandler;

import java.net.URI;
import java.util.concurrent.BlockingQueue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/1 17:01
 * Version: 1.0
 */
public class ConnectFactory {
    /**
     * 创建websocket客户端(支持cqhttp和mirai类型)
     * @param config 配置
     * @param queue 队列消息
     * @return 连接示例
     * @throws Exception 错误
     */
    public static ModWebSocketClient createWebsocketClient(BotConfig config, BlockingQueue<String> queue) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (config.getMirai()){
            builder.append(config.getUrl());
            builder.append("/all");
            builder.append("?verifyKey=");
            if (config.getIsAccessToken()) {
                builder.append(config.getToken());
            }
            builder.append("&qq=");
            builder.append(config.getBotId());
        }
        else {
            builder.append(config.getUrl());
            if (config.getIsAccessToken()) {
                builder.append("?access_token=");
                builder.append(config.getToken());
            }
        }
        String url = builder.toString();
        URI uri = new URI(url);
        ActionHandler actionHandler = new ActionHandler();
        return new ModWebSocketClient(uri, queue, actionHandler);
    }
}
