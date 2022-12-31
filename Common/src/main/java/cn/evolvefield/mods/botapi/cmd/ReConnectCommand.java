package cn.evolvefield.mods.botapi.cmd;


import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;


public class ReConnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        Constants.config.getBotConfig().setReconnect(isEnabled);
        ConfigHandler.save(Constants.config);
        if (isEnabled) {
            context.getSource().sendSuccess(Component.literal("已设置自动重连"), true);
        } else {
            context.getSource().sendSuccess(Component.literal("已关闭自动重连"), true);
        }

        return Command.SINGLE_SUCCESS;
    }
}
