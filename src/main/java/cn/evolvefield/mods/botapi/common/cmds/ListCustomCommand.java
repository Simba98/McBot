package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class ListCustomCommand {

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        StringBuilder out = new StringBuilder();
        for (String s : CustomCmdHandler.INSTANCE.getCustomCmdMap().keySet()) {
            out.append(s).append("\n");
        }
        context.getSource().sendSuccess(new StringTextComponent(out.toString()), true);
        return Command.SINGLE_SUCCESS;
    }
}
