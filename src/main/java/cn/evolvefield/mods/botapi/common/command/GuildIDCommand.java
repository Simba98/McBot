package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class GuildIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String id = context.getArgument("GuildID", String.class);
        BotApi.config.getCommon().setGuildOn(true);
        BotApi.config.getCommon().setGuildId(id);
        ConfigHandler.save(BotApi.config);
        context.getSource().sendSuccess(
                new TextComponent("已设置互通的频道号为:" + id), true);
        return Command.SINGLE_SUCCESS;
    }


}
