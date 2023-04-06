package cn.evolvefield.mods.botapi.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/6 19:43
 * Description:
 */
public enum MessageType {

    MSG(0),
    CMD(1),
    IMG(2),
    RECONNECT(3),
    PASS(4);


    private static final Map<Integer, MessageType> values = new HashMap<>();

    static {
        for (MessageType value : values()) {
            values.put(value.getType(), value);
        }
    }

    private final int type;

    MessageType(int type) {
        this.type = type;
    }

    public static MessageType valueOf(int type) {
        return values.get(type);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getType() {
        return type;
    }
}
