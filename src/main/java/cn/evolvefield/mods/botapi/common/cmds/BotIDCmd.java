package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class BotIDCmd {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        BotApi.config.getCommon().setBotId(id);
        ConfigHandler.onChange();
        context.getSource().sendSuccess(
                Component.literal("已设置机器人QQ号为:" + id), true);
        return 0;
    }


}
