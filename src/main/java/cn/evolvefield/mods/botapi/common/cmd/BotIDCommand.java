package cn.evolvefield.mods.botapi.common.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class BotIDCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("setBot")
                .then(Commands.argument("BotId", LongArgumentType.longArg())
                        .executes(BotIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        BotApi.config.getCommon().setBotId(id);
        BotData.setQQId(id);
        ConfigHandler.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                Component.literal("已设置机器人QQ号为:" + id), true);
        return 0;
    }


}