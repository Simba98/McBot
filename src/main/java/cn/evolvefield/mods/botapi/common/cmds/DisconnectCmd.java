package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;


public class DisconnectCmd {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (BotApi.service != null) {
            BotApi.service.close();
            if (BotApi.service.isClosed()) {
                context.getSource().sendSuccess(Component.literal("WebSocket已断开连接"), true);
            } else {
                context.getSource().sendSuccess(Component.literal("WebSocket目前未连接"), true);
            }
            BotApi.config.getCommon().setEnable(false);
            ConfigHandler.onChange();
        }
        return 0;
    }
}
