package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.CmdEvents;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 1:50
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CmdEventHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {

        if (event.getCommandSelection() == Commands.CommandSelection.ALL) {
            CmdEvents.init(event.getDispatcher());
        }

    }
}
