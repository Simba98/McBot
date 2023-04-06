package cn.evolvefield.mods.botapi.connect;

import cn.evolvefield.mods.botapi.config.BotConfig;
import org.java_websocket.WebSocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/5 19:40
 * Description:
 */
public class ConnectionFactory {

    public final WSServer ws;

    public final BlockingQueue<String> send ;




    public ConnectionFactory(BotConfig config){
        send =  new LinkedBlockingQueue<>();
        ws = new  WSServer(config, send);
    }


}
