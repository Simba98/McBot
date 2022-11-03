package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.common.cmds.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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
public class CmdEventHandler {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("mcbot")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("connect")
                                .then(Commands.literal("cqhttp")
                                        .executes(ConnectCmd::cqhttpCommonExecute)
                                        .then(Commands.argument("parameter", StringArgumentType.greedyString())
                                                .executes(ConnectCmd::cqhttpExecute)
                                        )
                                )
                                .then(Commands.literal("mirai")
                                        .executes(ConnectCmd::miraiCommonExecute)
                                        .then(Commands.argument("parameter", StringArgumentType.greedyString())
                                                .executes(ConnectCmd::miraiExecute)
                                        )
                                )

                        )
                        .then(Commands.literal("reload").executes(ReloadConfigCmd::execute))
                        .then(Commands.literal("disconnect").executes(DisconnectCmd::execute))
                        .then(Commands.literal("addChannelId")
                                .then(Commands.argument("ChannelId", StringArgumentType.greedyString())
                                        .executes(AddChannelIDCmd::execute)))
                        .then(Commands.literal("delChannelId")
                                .then(Commands.argument("ChannelId", StringArgumentType.greedyString())
                                        .executes(RemoveChannelIDCmd::execute)))
                        .then(Commands.literal("setGuild")
                                .then(Commands.argument("GuildId", StringArgumentType.greedyString())
                                        .executes(GuildIDCmd::execute)))
                        .then(Commands.literal("help").executes(HelpCmd::execute))
                        .then(Commands.literal("debug")
                                .then(Commands.argument("enabled", BoolArgumentType.bool())
                                        .executes(DebugCmd::execute)))
                        .then(Commands.literal("addGroup")
                                .then(Commands.argument("GroupId", LongArgumentType.longArg())
                                        .executes(AddGroupIDCmd::execute)))
                        .then(Commands.literal("removeGroup")
                                .then(Commands.argument("GroupId", LongArgumentType.longArg())
                                        .executes(RemoveGroupIDCmd::execute)))
                        .then(Commands.literal("setBot")
                                .then(Commands.argument("BotId", LongArgumentType.longArg())
                                        .executes(BotIDCmd::execute)))
                        .then(Commands.literal("setVerifyKey")
                                .then(Commands.argument("VerifyKey", StringArgumentType.string())
                                        .executes(VerifyKeyCmd::execute)))
                        .then(Commands.literal("status").executes(StatusCmd::execute))

                        .then(Commands.literal("receive")
                                .then(Commands.literal("all")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCmd::allExecute)))
                                .then(Commands.literal("chat")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCmd::chatExecute)))
                                .then(Commands.literal("cmd")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCmd::cmdExecute))))

                        .then(Commands.literal("send")
                                .then(Commands.literal("all")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::allExecute)))
                                .then(Commands.literal("join")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::joinExecute)))
                                .then(Commands.literal("leave")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::leaveExecute)))
                                .then(Commands.literal("death")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::deathExecute)))
                                .then(Commands.literal("chat")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::chatExecute)))
                                .then(Commands.literal("achievements")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::achievementsExecute)))
                                .then(Commands.literal("welcome")
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(SendCmd::welcomeExecute)))
                        )

        );
    }
}
