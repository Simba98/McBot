package cn.evolvefield.mods.botapi.util;

import cn.evolvefield.mods.botapi.model.action.common.SubType;
import cn.evolvefield.mods.botapi.model.event.MessageMap;
import cn.evolvefield.mods.botapi.model.event.global.Message;
import com.alibaba.fastjson2.JSONObject;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:03
 * Version: 1.0
 */
public class ListenerUtils {
    public static Class<? extends Message> getMessageType(String message) {
        return getMessageType(JSONObject.parseObject(message));
    }

    /**
     * 获取消息对应的实体类型
     *
     * @param object
     * @return
     */
    public static Class<? extends Message> getMessageType(JSONObject object) {
        String type = null;
        String postType = object.getString("post_type");
        if ("message".equals(postType)) {
            //消息类型
            String messageType = object.getString("message_type");
            if ("group".equals(messageType)) {
                //群聊消息类型
                type = "groupMessage";
            } else if ("private".equals(messageType)) {
                //私聊消息类型
                type = "privateMessage";
            }
        } else if ("request".equals(postType)) {
            //请求类型
            type = object.getString("request_type");
        } else if ("notice".equals(postType)) {
            //通知类型
            String noticeType = object.getString("notice_type");
            if ("notify".equals(noticeType) && SubType.POKE.getValue().equals(object.getString("sub_type"))) {
                //戳一戳单独处理
                Long groupId = object.getLong("group_id");
                if (!(groupId == null) && groupId > 0) {
                    type = "group_poke";//群聊戳一戳
                } else {
                    type = "friend_poke";//私聊戳一戳
                }
            } else {
                type = noticeType;
            }
        }
        return MessageMap.messageMap.get(type);
    }
}
