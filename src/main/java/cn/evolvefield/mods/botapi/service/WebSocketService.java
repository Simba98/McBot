package cn.evolvefield.mods.botapi.service;


import org.apache.commons.lang3.StringUtils;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 10:39
 * Version: 1.0
 */
@ServerEndpoint("/botapi/ws")//地址为 ws://127.0.0.1/botapi/ws
public class WebSocketService{
    private final static Logger log = LoggerFactory.getLogger(WebSocketService.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //使用map对象优化,线程安全，便于根据QQid来获取对应的WebSocket
    private static ConcurrentHashMap<Long, WebSocketService> websocketMap = new ConcurrentHashMap<>();
    //接收客户端QQ的id，指定需要推送的客户端
    private long id;

    @OnOpen
    public void onOpen(Session session) {
        try {
            long xSelfId = parseSelfId(session);
            if (xSelfId == 0L) {
                log.error("Get client self account failed");
                session.close();
                return;
            }
            websocketMap.put(id, this); //加入map中
            addOnlineCount();           //在线数加1
            log.info("账户 {} 连接, 当前已连接的客户端数量为 {}", xSelfId, getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO异常");
            e.printStackTrace();
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketMap.get(this.id) != null){
            websocketMap.remove(this.id);
            subOnlineCount();
            log.info("有连接关闭！当前已连接客户端数量为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法，根据业务要求进行处理，这里就简单地将收到的消息直接群发推送出去
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端" + id + "的信息:"+message);
        if(StringUtils.isNotBlank(message)){
            for(WebSocketService server : websocketMap.values()) {
                try {
                    server.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * 发生错误时的回调函数
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }




    /**
     * 格式化连接的 QQ 号
     *
     * @param session {@link Session}
     * @return QQ 号
     */
    private long parseSelfId(Session session) {
        String botId = session.getPathParameters().get("x-self-id");
        if (botId == null || botId.isEmpty()) {
            return 0L;
        }
        try {
            return Long.parseLong(botId);
        } catch (Exception e) {
            return 0L;
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
