package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;


public class DisconnectCommand {

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        if (BotApi.service != null) {
            BotApi.service.stop();
            if (!BotApi.service.ws.isOpen()) {
                context.getSource().sendSuccess(new StringTextComponent("WebSocket已断开连接"), true);
            } else {
                context.getSource().sendSuccess(new StringTextComponent("WebSocket目前未连接"), true);
            }
            ConfigHandler.cached().getCommon().setEnable(false);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }
}