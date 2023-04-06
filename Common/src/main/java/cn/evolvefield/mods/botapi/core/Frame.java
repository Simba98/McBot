package cn.evolvefield.mods.botapi.core;

import cn.evolvefield.mods.botapi.util.JsonsObject;

import java.util.Objects;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/6 19:45
 * Description:
 */
public class Frame {
    private final MessageType type;
    private final JsonsObject data;

    public Frame(String msg) {
        JsonsObject raw = new JsonsObject(msg);
        this.type = MessageType.valueOf(raw.optString("type"));
        this.data = raw.optJSONSObject("data");
    }

    public MessageType getType() {
        return type;
    }


    public JsonsObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

}
