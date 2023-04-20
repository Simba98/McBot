package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class GuildIDCommand {


    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        val id = context.getArgument("GuildID", String.class);
        ConfigHandler.cached().getCommon().setGuildOn(true);
        ConfigHandler.cached().getCommon().setGuildId(id);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置互通的频道号为:" + id), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }


}
