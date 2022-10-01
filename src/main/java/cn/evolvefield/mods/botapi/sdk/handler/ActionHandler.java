package cn.evolvefield.mods.botapi.sdk.handler;

import cn.evolvefield.mods.botapi.sdk.connection.ModWebSocketClient;
import cn.evolvefield.mods.botapi.sdk.model.action.common.ActionPath;
import cn.evolvefield.mods.botapi.sdk.util.ActionSendUtils;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

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
    public void onReceiveActionResp(JsonObject respJson) {
        String echo = respJson.get("echo").getAsString();
        ActionSendUtils actionSendUtils = apiCallbackMap.get(echo);
        if (actionSendUtils != null) {
            // 唤醒挂起的线程
            actionSendUtils.onCallback(respJson);
            apiCallbackMap.remove(echo);
        }
    }

    /**
     * @param channel Session
     * @param action  请求路径
     * @param params  请求参数
     * @return 请求结果
     */
    public JsonObject action(ModWebSocketClient channel, ActionPath action, JsonObject params) {
        if (!channel.isOpen()) {
            return null;
        }
        var reqJson = generateReqJson(action, params);
        ActionSendUtils actionSendUtils = new ActionSendUtils(channel, 3000L);
        apiCallbackMap.put(reqJson.get("echo").getAsString(), actionSendUtils);
        JsonObject result;
        try {
            result = actionSendUtils.send(reqJson);
        } catch (Exception e) {
            log.error("Request failed: {}", e.getMessage());
            result = new JsonObject();
            result.addProperty("status", "failed");
            result.addProperty("retcode", -1);
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
    private JsonObject generateReqJson(ActionPath action, JsonObject params) {
        var json = new JsonObject();
        json.addProperty("action", action.getPath());
        json.add("params", params);
        json.addProperty("echo", echo++);
        return json;
    }
}
