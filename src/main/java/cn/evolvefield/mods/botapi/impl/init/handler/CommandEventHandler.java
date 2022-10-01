package cn.evolvefield.mods.botapi.impl.init.handler;

import cn.evolvefield.mods.botapi.impl.common.cmds.TestCmd;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
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
public class CommandEventHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("mcbot")
                        .requires(commandSource -> commandSource.hasPermission(2))
                        .then(TestCmd.register())



        );
    }
}
