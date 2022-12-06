package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String id = context.getArgument("ChannelID", String.class);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            BotApi.config.getCommon().removeChannelId(id);

        } else {
            context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "并未出现！"), true);

        }
        ConfigHandler.save(BotApi.config);
        return Command.SINGLE_SUCCESS;
    }


}
