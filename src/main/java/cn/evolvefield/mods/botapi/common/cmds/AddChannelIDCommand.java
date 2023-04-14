package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.var;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class AddChannelIDCommand {


    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        ConfigHandler.cached().getCommon().setGuildOn(true);
        if (ConfigHandler.cached().getCommon().getChannelIdList().contains(id)) {
            context.getSource().sendSuccess(new StringTextComponent("子频道号:" + id + "已经出现了！"), true);
        } else {
            ConfigHandler.cached().getCommon().addChannelId(id);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
