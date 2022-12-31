package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

public class AddGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GroupId", Long.class);
        if (Constants.config.getCommon().getGroupIdList().contains(id)) {
            context.getSource().sendSuccess(Component.literal("QQ群号:" + id + "已经出现了！"), true);
        } else {
            Constants.config.getCommon().addGroupId(id);
            context.getSource().sendSuccess(Component.literal("已成功添加QQ群号:" + id + "！"), true);
        }
        ConfigHandler.save(Constants.config);
        return Command.SINGLE_SUCCESS;
    }


}
