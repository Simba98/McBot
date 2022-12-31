package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.CmdEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/12 9:12
 * Version: 1.0
 */
public class CmdEventHandler {
    public static void init() {

        CommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext, commandSelection) -> {
            if (commandSelection.includeDedicated)
                CmdEvents.init(dispatcher);
        });
    }
}
