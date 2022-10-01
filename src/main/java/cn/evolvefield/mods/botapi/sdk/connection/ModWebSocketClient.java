package cn.evolvefield.mods.botapi.sdk.connection;

import cn.evolvefield.mods.botapi.sdk.core.Bot;
import cn.evolvefield.mods.botapi.sdk.handler.ActionHandler;
import cn.evolvefield.mods.botapi.sdk.util.json.util.JsonsObject;
import cn.evolvefield.mods.botapi.sdk.util.websocket.client.WebSocketClient;
import cn.evolvefield.mods.botapi.sdk.util.websocket.handshake.ServerHandshake;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.BlockingQueue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/1 16:53
 * Version: 1.0
 */
public class ModWebSocketClient extends WebSocketClient implements Connection {
    private final static String API_RESULT_KEY = "echo";

    private static final String FAILED_STATUS = "failed";

    private static final String RESULT_STATUS_KEY = "status";
    private static final String HEART_BEAT = "heartbeat";
    private static final String LIFE_CYCLE = "lifecycle";
    private BlockingQueue<String> queue;

    private static final Logger log = LoggerFactory.getLogger(ModWebSocketClient.class);
    private static ModWebSocketClient instance;

    private ActionHandler actionHandler;
    private int sendFlag = 0;
    private String result = null;
    public ModWebSocketClient(URI serverUri, BlockingQueue<String> queue, ActionHandler actionHandler) {
        super(serverUri);
        this.queue = queue;
        this.actionHandler = actionHandler;
    }

    public Bot createBot(){
        return new Bot(this, actionHandler);
    }


//    public static WebSocketClient getInstance() {
//        try {
//            if (instance != null) {
//                if (instance.getReadyState() == ReadyState.NOT_YET_CONNECTED
//                        || instance.getReadyState() == ReadyState.CLOSED) {
//                    if (instance.isClosed()) {
//                        instance.reconnect();
//                    }
//                }
//            } else {
//                instance = new WebSocketClient(wsUrl);
//                instance.connect();
//            }
//        } catch (Exception ex) {
//            instance = null;
//            log.error(" websocket 构建实例出现问题！！" + ex);
//        }
//        return instance;
//    }



    public String sendStr(String text){
        synchronized(this){
            sendFlag = 1;
            this.send(text);
            while(sendFlag != 0){
                log.debug("等待返回值中 =============== " + sendFlag);
            }
            return result;
        }
    }



    /**
     * 建立连接
     *
     * @param serverHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("已连接到服务器：{}，开始监听事件", this.uri);

    }

    /**
     * 收到消息
     *
     * @param message
     */
    @Override
    public void onMessage(String message) {
        JsonObject jsonObject = new JsonsObject(message).get();

        if (message != null && !jsonObject.has(HEART_BEAT) && !jsonObject.has(LIFE_CYCLE)) {//过滤心跳
            result = message;
            sendFlag = 0;
            // if resp contains echo field, this resp is action resp, else event resp.
            if (jsonObject.has(API_RESULT_KEY)) {
                if (FAILED_STATUS.equals(jsonObject.get(RESULT_STATUS_KEY).getAsString())) {
                    log.error("请求失败: {}", jsonObject.get("wording"));
                }
                actionHandler.onReceiveActionResp(jsonObject);
            } else {
                queue.add(message);
            }

        }


    }

    @Override
    public void onClose(int i, String s, boolean b) {
        result = null;
        sendFlag = 0;
    }

    /**
     * 出现异常
     *
     * @param e
     */
    @Override
    public void onError(Exception e) {
        result = null;
        sendFlag = 0;
        log.warn(e.getMessage());
    }

    @Override
    public void create() {
        super.connect();
    }
}
