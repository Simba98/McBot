package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

public class RemoveGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupID", Long.class);
        if (Constants.config.getCommon().getGroupIdList().contains(id)) {
            Constants.config.getCommon().removeGroupId(id);

        } else {
            context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save(Constants.config);
        return Command.SINGLE_SUCCESS;
    }


}
