package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.TickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:47
 * Version: 1.0
 */
public class TickEventHandler {

    public static void init() {
        ServerTickEvents.START_SERVER_TICK.register(TickEvents::init);
    }
}
