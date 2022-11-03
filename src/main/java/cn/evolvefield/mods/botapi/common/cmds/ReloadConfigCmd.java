package cn.evolvefield.mods.botapi.common.cmds;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/28 13:37
 * Version: 1.0
 */
public class ReloadConfigCmd {
    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            BotApi.config = ConfigHandler.load();
            if (BotApi.config == null) {
                context.getSource().sendSuccess(Component.literal("重载配置失败"), true);
            }
            context.getSource().sendSuccess(Component.literal("重载配置成功"), true);
        } catch (Exception e) {
            context.getSource().sendSuccess(Component.literal("重载配置失败"), true);

        }
        return 1;
    }
}
