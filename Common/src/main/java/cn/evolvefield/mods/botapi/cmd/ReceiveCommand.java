package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

public class ReceiveCommand {

    public static int allExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        Constants.config.getStatus().setRECEIVE_ENABLED(isEnabled);
        ConfigHandler.save(Constants.config);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    Component.literal("全局接收群消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    Component.literal("全局接收群消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }

    public static int chatExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        Constants.config.getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            Constants.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.save(Constants.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内聊天消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(Constants.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内聊天消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;

    }

    public static int cmdExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        Constants.config.getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            Constants.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.save(Constants.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为打开"), true);
        } else {
            ConfigHandler.save(Constants.config);
            context.getSource().sendSuccess(
                    Component.literal("接收群内命令消息开关已被设置为关闭"), true);
        }
        return Command.SINGLE_SUCCESS;
    }
}
