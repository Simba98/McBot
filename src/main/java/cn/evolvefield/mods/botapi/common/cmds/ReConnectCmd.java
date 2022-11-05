package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;


public class ReConnectCmd {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getBotConfig().setReconnect(isEnabled);
        ConfigHandler.onChange();
        if (isEnabled) {
            context.getSource().sendSuccess(new TextComponent("已设置自动重连"), true);
        } else {
            context.getSource().sendSuccess(new TextComponent("已关闭自动重连"), true);
        }

        return 0;
    }
}
