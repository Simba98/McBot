package cn.evolvefield.mods.botapi.web.connect;

import blue.endless.jankson.Jankson;
import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.core.action.Request;
import cn.evolvefield.mods.botapi.handler.EventHandler;
import cn.evolvefield.mods.botapi.util.GsonUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/5 18:58
 * Description:
 */
public class WSServer extends WebSocketServer {

    private final Logger log = LoggerFactory.getLogger("BotServer");

    private final BlockingQueue<String> send;

    private final MinecraftServer server;
    private final Jankson jankson = Jankson.builder().build();

    public WSServer(BotConfig config, BlockingQueue<String> send, MinecraftServer server){
        super(new InetSocketAddress(config.getHost(), config.getPort()));
        this.send = send;
        this.server = server;
    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        log.info("-----------------已连接--------------------");
        for(Iterator<String> it = handshake.iterateHttpFields(); it.hasNext();) {
            String key = it.next();
            System.out.println(key+":"+handshake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        send.forEach(conn::send);
        var event = new EventHandler(server, conn, jankson.fromJson(message, Request.class));
        event.run();



    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        log.info("------------------正在关闭-------------------");
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        log.error("------------------出现问题-------------------\n"+ ex.getMessage());
    }

    @Override
    public void onStart() {
        log.info("------------------等待连接-------------------");
        //setConnectionLostTimeout(1000);
    }



}
