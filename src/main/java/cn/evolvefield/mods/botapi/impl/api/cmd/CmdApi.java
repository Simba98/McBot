package cn.evolvefield.mods.botapi.impl.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.impl.init.handler.CustomCmdHandler;
import cn.evolvefield.mods.botapi.sdk.core.Bot;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:56
 * Version: 1.0
 */
public class CmdApi {
    private static StringBuilder CmdMain(CustomCmd customCmd){
        StringBuilder result = new StringBuilder();
        BotApi.SERVER.getCommands().performPrefixedCommand(CustomCmdRun.CUSTOM, customCmd.getCmdContent());

        for (String s : CustomCmdRun.CUSTOM.outPut) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        CustomCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    public static void GroupCmd(Bot bot, CustomCmd customCmd, GroupMessageEvent event) {
        bot.sendGroupMsg(event.getGroupId(), CmdMain(customCmd).toString(), true);
    }


    public static void invokeCommandMain(GroupMessageEvent event){
        String[] formatMsg = event.getMessage().split(" ");
        String commandBody = formatMsg[0].substring(1);

        if (!event.getSender().getRole().equals("MEMBER") || !event.getSender().getRole().equals("member")) {
            CustomCmdHandler.getInstance().getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandBody))
                    .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));
        }
        CustomCmdHandler.getInstance().getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandBody))
                .forEach(customCmd -> GroupCmd(BotApi.bot, customCmd, event));

    }
}
