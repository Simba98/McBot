package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.var;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeChannelId(id);
        } else {
            context.getSource().sendSuccess(new StringTextComponent("子频道号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}