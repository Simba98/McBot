package cn.evolvefield.mods.botapi.impl.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.sdk.util.MsgUtils;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 1:46
 * Version: 1.0
 */
public class TestCmd {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("test")
                        .executes(TestCmd::execute);
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        //System.out.println(BotApi.service.sendStr(SendMessage.Group(337631140, MsgUtils.builder().text("1").build())));
        System.out.println(BotApi.bot.sendGroupMsg(337631140, MsgUtils.builder().text("1").build(), true));
        return 0;
    }
}
