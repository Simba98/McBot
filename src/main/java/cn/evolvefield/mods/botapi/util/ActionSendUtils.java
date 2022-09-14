package cn.evolvefield.mods.botapi.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 15:06
 * Version: 1.0
 */
@Slf4j
public class ActionSendUtils extends Thread {
    private final Session session;

    private final long requestTimeout;

    private JSONObject resp;

    /**
     * @param session        {@link Session}
     * @param requestTimeout Request Timeout
     */
    public ActionSendUtils(Session session, Long requestTimeout) {
        this.session = session;
        this.requestTimeout = requestTimeout;
    }

    /**
     * @param req Request json data
     * @return Response json data
     * @throws IOException          exception
     * @throws InterruptedException exception
     */
    public JSONObject send(JSONObject req) throws IOException, InterruptedException {
        synchronized (session) {
            log.debug("[Action] {}", req.toJSONString());
            session.getAsyncRemote().sendText(req.toJSONString());
        }
        synchronized (this) {
            this.wait(requestTimeout);
        }
        return resp;
    }

    /**
     * @param resp Response json data
     */
    public void onCallback(JSONObject resp) {
        this.resp = resp;
        synchronized (this) {
            this.notify();
        }
    }
}
