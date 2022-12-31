package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class AddChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        Constants.config.getCommon().setGuildOn(true);
        if (Constants.config.getCommon().getChannelIdList().contains(id)) {
            context.getSource().sendSuccess(Component.literal("子频道号:" + id + "已经出现了！"), true);
        } else {
            Constants.config.getCommon().addChannelId(id);
        }
        ConfigHandler.save(Constants.config);
        return Command.SINGLE_SUCCESS;
    }


}
