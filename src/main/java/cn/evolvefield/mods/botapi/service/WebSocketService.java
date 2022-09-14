package cn.evolvefield.mods.botapi.service;


import cn.evolvefield.mods.botapi.core.Bot;
import cn.evolvefield.mods.botapi.core.BotContainer;
import cn.evolvefield.mods.botapi.core.BotFactory;
import cn.evolvefield.mods.botapi.handler.ActionHandler;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
    private final static String API_RESULT_KEY = "echo";

    private static final String FAILED_STATUS = "failed";

    private static final String RESULT_STATUS_KEY = "status";
    private final static Logger log = LoggerFactory.getLogger(WebSocketService.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //机器人容器，储存着所有客户端
    private BotContainer botContainer;
    private BotFactory botFactory;

    private final ActionHandler actionHandler;
    @OnOpen
    public void onOpen(Session session) {
        try {
            long xSelfId = parseSelfId(session);
            if (xSelfId == 0L) {
                log.error("Get client self account failed");
                session.close();
                return;
            }
            botContainer.robots.put(xSelfId, botFactory.createBot(xSelfId, session));
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
    public void onClose(Session session) {
        long xSelfId = parseSelfId(session);
        if (xSelfId == 0L) {
            return;
        }
        botContainer.robots.remove(xSelfId);
        log.warn("账户 {} 断开连接", xSelfId);

    }

    /**
     * 收到客户端消息后调用的方法，根据业务要求进行处理，这里就简单地将收到的消息直接群发推送出去
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        long xSelfId = parseSelfId(session);
        Bot bot = botContainer.robots.get(xSelfId);
        if (bot == null) {
            bot = botFactory.createBot(xSelfId, session);
            botContainer.robots.put(xSelfId, bot);
        }
        bot.setSession(session);
        JSONObject result = JSON.parseObject(message);
        log.debug("[Event] {}", result.toJSONString());
        // if resp contains echo field, this resp is action resp, else event resp.
        if (result.containsKey(API_RESULT_KEY)) {
            if (FAILED_STATUS.equals(result.get(RESULT_STATUS_KEY))) {
                log.error("Request failed: {}", result.get("wording"));
            }
            actionHandler.onReceiveActionResp(result);
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
