package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class RemoveGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val id = context.getArgument("GroupID", Long.class);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            ConfigHandler.cached().getCommon().removeGroupId(id);
        } else {
            context.getSource().sendSuccess(new TextComponent("QQ群号:" + id + "并未出现！"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
