package cn.evolvefield.mods.botapi.handler;

import cn.evolvefield.mods.botapi.model.action.common.ActionPath;
import cn.evolvefield.mods.botapi.util.ActionSendUtils;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 15:05
 * Version: 1.0
 */
@Slf4j
public class ActionHandler {
    /**
     * 请求回调数据
     */
    private final Map<String, ActionSendUtils> apiCallbackMap = new HashMap<>();
    /**
     * 用于标识请求，可以是任何类型的数据，OneBot 将会在调用结果中原样返回
     */
    private int echo = 0;

    /**
     * 处理响应结果
     *
     * @param respJson 回调结果
     */
    public void onReceiveActionResp(JSONObject respJson) {
        String echo = respJson.get("echo").toString();
        ActionSendUtils actionSendUtils = apiCallbackMap.get(echo);
        if (actionSendUtils != null) {
            // 唤醒挂起的线程
            actionSendUtils.onCallback(respJson);
            apiCallbackMap.remove(echo);
        }
    }

    /**
     * @param session Session
     * @param action  请求路径
     * @param params  请求参数
     * @return 请求结果
     */
    public JSONObject action(Session session, ActionPath action, Map<String, Object> params) {
        if (!session.isOpen()) {
            return null;
        }
        JSONObject reqJson = generateReqJson(action, params);
        ActionSendUtils actionSendUtils = new ActionSendUtils(session, webSocketProperties.getRequestTimeout());
        apiCallbackMap.put(reqJson.getString("echo"), actionSendUtils);
        JSONObject result;
        try {
            result = actionSendUtils.send(reqJson);
        } catch (Exception e) {
            log.error("Request failed: {}", e.getMessage());
            result = new JSONObject();
            result.put("status", "failed");
            result.put("retcode", -1);
        }
        return result;
    }

    /**
     * 构建请求数据
     * {"action":"send_private_msg","params":{"user_id":10001000,"message":"你好"},"echo":"123"}
     *
     * @param action 请求路径
     * @param params 请求参数
     * @return 请求数据结构
     */
    private JSONObject generateReqJson(ActionPath action, Map<String, Object> params) {
        return new JSONObject() {{
            put("action", action.getPath());
            if (params != null && !params.isEmpty()) {
                put("params", params);
            }
            put("echo", echo++);
        }};
    }
}
