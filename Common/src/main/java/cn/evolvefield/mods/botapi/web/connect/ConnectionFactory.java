package cn.evolvefield.mods.botapi.web.connect;

import cn.evolvefield.mods.botapi.config.BotConfig;
import net.minecraft.server.MinecraftServer;

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



    public ConnectionFactory(BotConfig config, MinecraftServer server){
        send =  new LinkedBlockingQueue<>();
        ws = new  WSServer(config, send, server);
    }


}
