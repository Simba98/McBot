package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCmd {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setDebuggable(isEnabled);
        ConfigHandler.onChange();
        if (isEnabled) {
            context.getSource().sendSuccess(new TextComponent("已开启开发者模式"), true);
        } else {
            context.getSource().sendSuccess(new TextComponent("已关闭开发者模式"), true);
        }

        return 0;
    }
}
