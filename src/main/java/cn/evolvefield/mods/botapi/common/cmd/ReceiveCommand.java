package cn.evolvefield.mods.botapi.common.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class ReceiveCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("receive")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::allExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::chatExecute)))
                .then(literal("cmd")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::cmdExecute)))
                ;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setRECEIVE_ENABLED(isEnabled);
        ConfigHandler.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    Component.literal("全局接收群消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    Component.literal("全局接收群消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内聊天消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内聊天消息开关已被设置为关闭"), true);
        }
        return 0;

    }

    public static int cmdExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为关闭"), true);
        }
        return 0;
    }
}
