package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class ListCustomCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        StringBuilder out = new StringBuilder();
        for (String s : CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet()) {
            out.append(s).append("\n");
        }
        context.getSource().sendSuccess(new TextComponent(out.toString()), true);
        return Command.SINGLE_SUCCESS;
    }
}
