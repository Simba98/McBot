package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class AddGroupIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("GroupId", Long.class);
        if (BotApi.config.getCommon().getGroupIdList().contains(id)) {
            context.getSource().sendSuccess(new TextComponent("QQ群号:" + id + "已经出现了！"), true);
        } else {
            BotApi.config.getCommon().addGroupId(id);
            context.getSource().sendSuccess(new TextComponent("已成功添加QQ群号:" + id + "！"), true);
        }
        ConfigHandler.save(BotApi.config);
        return Command.SINGLE_SUCCESS;
    }


}
