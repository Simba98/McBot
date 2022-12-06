package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;


public class SendCommand {


    public static int welcomeExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_WELCOME_ENABLE(isEnabled);
        ConfigHandler.save(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new TextComponent("发送新人加入QQ群的消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setSEND_ENABLED(isEnabled);
        ConfigHandler.save(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new TextComponent("全局发送消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int joinExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家加入游戏消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int leaveExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家离开游戏消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int deathExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家死亡游戏消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家聊天游戏消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int achievementsExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getStatus().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled) {
            BotApi.config.getStatus().setSEND_ENABLED(true);
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(BotApi.config);
            context.getSource().sendSuccess(
                    new TextComponent("发送玩家成就游戏消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

}
