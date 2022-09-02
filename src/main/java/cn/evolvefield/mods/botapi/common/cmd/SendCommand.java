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


public class SendCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("send")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::allExecute)))
                .then(literal("join")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::joinExecute)))
                .then(literal("leave")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::leaveExecute)))
                .then(literal("death")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::deathExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::chatExecute)))
                .then(literal("achievements")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::achievementsExecute)))
                .then(literal("welcome")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::welcomeExecute)))
                ;
    }

    public static int welcomeExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_WELCOME_ENABLE(isEnabled);
        ConfigHandler.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    Component.literal("发送新人加入QQ群的消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    Component.literal("发送新人加入QQ群的消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setSEND_ENABLED(isEnabled);
        ConfigHandler.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    Component.literal("全局发送消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    Component.literal("全局发送消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int joinExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家加入游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家加入游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int leaveExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家离开游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家离开游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int deathExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家死亡游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家死亡游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家聊天游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家聊天游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int achievementsExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家成就游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    Component.literal("发送玩家成就游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

}
