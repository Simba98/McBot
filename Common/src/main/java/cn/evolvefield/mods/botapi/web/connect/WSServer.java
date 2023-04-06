package cn.evolvefield.mods.botapi.web.connect;

import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.util.JsonsObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/5 18:58
 * Description:
 */
public class WSServer extends WebSocketServer {

    private final Logger log = LoggerFactory.getLogger("BotServer");
    protected final ExecutorService eventExecutor;

    private final BlockingQueue<String> send;
    private final BlockingQueue<String> receive;

    public WSServer(BotConfig config, BlockingQueue<String> send){
        super(new InetSocketAddress(config.getHost(), config.getPort()));
        this.send = send;
        this.receive =  new LinkedBlockingQueue<>();
        this.eventExecutor = Executors.newSingleThreadExecutor(r -> new Thread(r, "Event Executor"));

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
        JsonsObject json = new JsonsObject(message);
        receive.add(message);
        log.info(message);




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
        setConnectionLostTimeout(1000);
    }

    public void shutdown(){
        eventExecutor.shutdown();
        this.stop();
    }


}
