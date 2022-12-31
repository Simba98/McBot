package cn.evolvefield.mods.botapi.cmd;


import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;


public class DisconnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (CommonBotApi.service != null) {
            CommonBotApi.service.close();
            if (CommonBotApi.service.isClosed()) {
                context.getSource().sendSuccess(Component.literal("WebSocket已断开连接"), true);
            } else {
                context.getSource().sendSuccess(Component.literal("WebSocket目前未连接"), true);
            }
            Constants.config.getCommon().setEnable(false);
            ConfigHandler.save(Constants.config);
        }
        return Command.SINGLE_SUCCESS;
    }
}
