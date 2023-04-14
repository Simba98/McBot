package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.var;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class VerifyKeyCommand {


    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        var id = context.getArgument("VerifyKey", String.class);
        ConfigHandler.cached().getBotConfig().setToken(id);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置Mirai框架的VerifyKey为:" + id), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
