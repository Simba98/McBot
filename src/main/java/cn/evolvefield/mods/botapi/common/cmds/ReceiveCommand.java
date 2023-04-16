package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class ReceiveCommand {

    public static int allExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(isEnabled);
        if (isEnabled) {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局接收群消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局接收群消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }

    public static int chatExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内聊天消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内聊天消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;

    }

    public static int cmdExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getStatus().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled) {
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内命令消息开关已被设置为打开"), true);
        } else {
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内命令消息开关已被设置为关闭"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }
}