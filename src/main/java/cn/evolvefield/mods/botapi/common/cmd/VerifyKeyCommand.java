package cn.evolvefield.mods.botapi.common.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class VerifyKeyCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("setVerifyKey")
                .then(Commands.argument("VerifyKey", StringArgumentType.string())
                        .executes(VerifyKeyCommand::execute));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("VerifyKey", String.class);
        BotApi.config.getMirai().setVerifyKey(id);
        ConfigHandler.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                Component.literal("已设置Mirai框架的VerifyKey为:" + id), true);
        return 0;
    }


}