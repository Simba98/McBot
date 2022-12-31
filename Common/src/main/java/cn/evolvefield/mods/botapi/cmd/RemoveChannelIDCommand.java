package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

public class RemoveChannelIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("ChannelID", String.class);
        if (Constants.config.getCommon().getChannelIdList().contains(id)) {
            Constants.config.getCommon().removeChannelId(id);

        } else {
            context.getSource().sendSuccess(Component.literal("子频道号:" + id + "并未出现！"), true);

        }
        ConfigHandler.save(Constants.config);
        return Command.SINGLE_SUCCESS;
    }


}
