package cn.evolvefield.mods.botapi.common.cmd;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class DisconnectCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("disconnect").executes(DisconnectCommand::execute);
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        WebSocketService.client.close();
        if (WebSocketService.client.isClosed()) {
            context.getSource().sendSuccess(Component.literal("WebSocket已断开连接"), true);
        } else {
            context.getSource().sendSuccess(Component.literal("WebSocket目前未连接"), true);
        }
        BotApi.config.getCommon().setEnable(false);
        ConfigHandler.saveBotConfig(BotApi.config);
        return 0;
    }
}
