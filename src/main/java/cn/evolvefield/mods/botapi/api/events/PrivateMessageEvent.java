package cn.evolvefield.mods.botapi.api.events;


import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:45
 * Version: 1.0
 */
public class PrivateMessageEvent extends Event {
    private final String nickname;//发送人昵称
    private final long user_id;//发送人qq
    private final String json;//消息原始文本
    private long self_id;//机器人qq
    private String message;//收到消息
    private String sub_type;//消息子类型
    private long temp_source;//临时消息会话来源
    private String message_id;//消息id
    private long group_id;//消息群号
    private String group_name;//群名称
    private List<MiraiMessage> raw_message;//Mirai消息链

    //cq-http框架触发事件
    public PrivateMessageEvent(String json, long self_id, String message, String nickname, long user_id, String sub_type,
                               long temp_source) {
        this.self_id = self_id;
        this.message = message;
        this.nickname = nickname;
        this.user_id = user_id;
        this.sub_type = sub_type;
        this.json = json;

        this.temp_source = temp_source;
    }

    //Mirai框架触发事件(临时消息)
    public PrivateMessageEvent(String json, List<MiraiMessage> raw_message, long user_id, String memberName, long group_id, String group_name) {
        this.json = json;
        this.raw_message = raw_message;
        this.user_id = user_id;
        this.nickname = memberName;
        this.group_id = group_id;

        this.group_name = group_name;
    }

    //Mirai框架触发事件(好友消息)
    public PrivateMessageEvent(String json, List<MiraiMessage> raw_message, long user_id, String nickname) {
        this.json = json;
        this.raw_message = raw_message;
        this.user_id = user_id;
        this.nickname = nickname;
    }


    //getter
    public long getGroupId() {
        return user_id;
    }

    public String getGroupName() {
        return group_name;
    }

    public List<MiraiMessage> getMiraiMessage() {
        return raw_message;
    }

    public long getTempSource() {
        return temp_source;
    }

    public String getMessage() {
        return this.message;
    }

    public long getSelfId() {
        return this.self_id;
    }

    public String getNickName() {
        return this.nickname;
    }

    public long getUserId() {
        return this.user_id;
    }

    public String getSubType() {
        return this.sub_type;
    }

    public String getJson() {
        return this.json;
    }
}
