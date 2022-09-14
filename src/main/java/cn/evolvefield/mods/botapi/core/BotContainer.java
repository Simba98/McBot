package cn.evolvefield.mods.botapi.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:52
 * Version: 1.0
 */
public class BotContainer {
    /**
     * Bot容器
     */
    public Map<Long, Bot> robots = new ConcurrentHashMap<>();

}
