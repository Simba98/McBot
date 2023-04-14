package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;


public class ReConnectCommand {

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        ConfigHandler.cached().getBotConfig().setReconnect(isEnabled);
        if (isEnabled) {
            context.getSource().sendSuccess(new StringTextComponent("已设置自动重连"), true);
        } else {
            context.getSource().sendSuccess(new StringTextComponent("已关闭自动重连"), true);
        }

        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }
}
