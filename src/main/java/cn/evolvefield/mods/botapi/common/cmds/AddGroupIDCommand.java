package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class AddGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupId", Long.class);
        if (ConfigHandler.cached().getCommon().getGroupIdList().contains(id)) {
            context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "已经出现了！"), true);
        } else {
            ConfigHandler.cached().getCommon().addGroupId(id);
            context.getSource().sendSuccess(Component.literal("已成功添加QQ群号:" + id + "！"), true);
        }
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
