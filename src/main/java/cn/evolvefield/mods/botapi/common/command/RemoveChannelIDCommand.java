package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("ChannelID", String.class);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeChannelId(id);
        } else {
            context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
