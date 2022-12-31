package cn.evolvefield.mods.botapi.cmd;

import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

;

public class GuildIDCommand {


    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var id = context.getArgument("GuildID", String.class);
        Constants.config.getCommon().setGuildOn(true);
        Constants.config.getCommon().setGuildId(id);
        ConfigHandler.save(Constants.config);
        context.getSource().sendSuccess(
                Component.literal("已设置互通的频道号为:" + id), true);
        return Command.SINGLE_SUCCESS;
    }


}
