package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        Constants.config.getCommon().setDebuggable(isEnabled);
        ConfigHandler.save(Constants.config);
        if (isEnabled) {
            context.getSource().sendSuccess(Component.literal("已开启开发者模式"), true);
        } else {
            context.getSource().sendSuccess(Component.literal("已关闭开发者模式"), true);
        }

        return Command.SINGLE_SUCCESS;
    }
}
