package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class BotIDCommand {


    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        ConfigHandler.cached().getCommon().setBotId(id);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置机器人QQ号为:" + id), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
