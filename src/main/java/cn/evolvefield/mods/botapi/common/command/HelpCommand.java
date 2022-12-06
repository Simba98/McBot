package cn.evolvefield.mods.botapi.common.command;


import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

public class HelpCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        String toSend =


                "群服互联使用说明:\n"
                        + "如果你是第一次使用请按照以下步骤设置\n"
                        + "1.请先开启机器人框架，go-cqhttp或者mirai\n"
                        + "2.请使用/mcbot addGroup <GroupId> 添加互通的群\n"
                        + "3.请使用/mcbot setBot <BotId> 设置机器人的qq号\n"
                        + "4.如果使用的是onebot-mirai，同时打开了VerifyKey验证，请输入/mcbot setVerifyKey <VerifyKey> 设置\n"
                        + "5.准备工作完成，请使用/mcbot connect <cqhttp/mirai> <host:port> 与框架对接\n"
                        + "在框架默认配置下，请使用/mcbot connect <cqhttp/mirai>\n"
                        + "*************************************\n"
                        + "全部命令：\n"
                        + "/mcbot connect <cqhttp/mirai> <host:port>\n"
                        + "/mcbot setFrame <cqhttp/mirai>\n"
                        + "/mcbot addGroup <GroupId>\n"
                        + "/mcbot removeGroup <GroupId>\n"
                        + "/mcbot setBot <BotId>\n"
                        + "/mcbot setVerifyKey <VerifyKey>\n"
                        + "/mcbot receive <all|chat|cmd> <true|false>\n"
                        + "/mcbot send <all|join|leave|death|achievements|welcome> <true|false>\n"
                        + "/mcbot status\n"
                        + "/mcbot help\n"
                        + "/mcbot reload   重载配置\n"
                        + "*************************************\n"
                        + "感谢您的支持，如有问题请联系我\n"
                        + "QQ群：720975019找群主\n"
                        + "Github：";

        String url = "https://github.com/Nova-Committee/Bot-Connect/issues/new";
        String end = "提交问题";

        Component urlC = new TextComponent(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Nova-Committee/Bot-Connect/issues/new")));
        Component endC = new TextComponent(end);
        context.getSource().sendSuccess(new TextComponent(toSend).append(urlC).append(endC), true);
        return 1;
    }
}
