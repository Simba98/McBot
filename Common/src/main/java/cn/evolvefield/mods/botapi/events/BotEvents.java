package cn.evolvefield.mods.botapi.events;

import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.cmd.custom.CmdApi;
import cn.evolvefield.onebot.sdk.listener.SimpleListener;
import cn.evolvefield.onebot.sdk.model.event.EventDispatchers;
import cn.evolvefield.onebot.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.message.GuildMessageEvent;
import cn.evolvefield.onebot.sdk.model.event.notice.GroupDecreaseNoticeEvent;
import cn.evolvefield.onebot.sdk.model.event.notice.GroupIncreaseNoticeEvent;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 14:02
 * Description:
 */
public class BotEvents {
    public static void init(EventDispatchers dispatchers) {

        GroupChatHandler(dispatchers);
        GroupCmdsHandler(dispatchers);
        GroupMemberNotice(dispatchers);
        GuildChatHandler(dispatchers);
        GuildCmdsHandler(dispatchers);
        dispatchers.start(10);//线程组处理任务
    }

    private static void GroupChatHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (Constants.config.getCommon().getGroupIdList().contains(event.getGroupId())//判断是否是配置中的群
                        && !event.getMessage().startsWith(Constants.config.getCmd().getCommandStart())//过滤命令前缀
                        && !event.getMessage().contains("[CQ:")//去除字符之外的其他cq码
                        && Constants.config.getStatus().isRECEIVE_ENABLED()//总接受开关
                        && Constants.config.getStatus().isR_CHAT_ENABLE()//接受聊天开关
                        && event.getUserId() != Constants.config.getCommon().getBotId()
                ) {
                    String send = event.getMessage();
                    if (Constants.config.getCmd().isQqChatPrefixEnable()) {
                        var split = event.getMessage().split(" ");
                        if (Constants.config.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String toSend = String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", Constants.config.getCmd().getQqPrefix(), event.getGroupId(), event.getSender().getNickname(), send);
                    TickEvents.getToSendQueue().add(toSend);
                }
            }
        });
    }

    private static void GroupCmdsHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupMessageEvent>() {
            @Override
            public void onMessage(GroupMessageEvent event) {
                if (Constants.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && event.getMessage().startsWith(Constants.config.getCmd().getCommandStart())//命令前缀
                        && Constants.config.getStatus().isRECEIVE_ENABLED()//总接受开关
                        && Constants.config.getStatus().isR_COMMAND_ENABLED()//接受命令开关
                ) {
                    CmdApi.invokeCommandGroup(event);
                }
            }
        });
    }

    private static void GroupMemberNotice(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GroupDecreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupDecreaseNoticeEvent event) {
                if (Constants.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && Constants.config.getStatus().isRECEIVE_ENABLED()
                        && Constants.config.getStatus().isS_WELCOME_ENABLE()) {
                    CommonBotApi.bot.sendGroupMsg(event.getGroupId(), Constants.config.getCmd().getWelcomeNotice(), true);
                }
            }
        });

        dispatchers.addListener(new SimpleListener<GroupIncreaseNoticeEvent>() {
            @Override
            public void onMessage(GroupIncreaseNoticeEvent event) {
                if (Constants.config.getCommon().getGroupIdList().contains(event.getGroupId())
                        && Constants.config.getStatus().isRECEIVE_ENABLED()
                        && Constants.config.getStatus().isS_WELCOME_ENABLE()) {
                    CommonBotApi.bot.sendGroupMsg(event.getGroupId(), Constants.config.getCmd().getLeaveNotice(), true);
                }
            }
        });

    }

    private static void GuildChatHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (event.getGuildId().equals(Constants.config.getCommon().getGuildId())
                        && Constants.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && !event.getMessage().startsWith(Constants.config.getCmd().getCommandStart())//过滤命令前缀
                        && !event.getMessage().contains("[CQ:")//去除字符之外的其他cq码
                        && Constants.config.getStatus().isRECEIVE_ENABLED()//总接受开关
                        && Constants.config.getStatus().isR_CHAT_ENABLE()//接受聊天开关
                        && event.getUserId() != Constants.config.getCommon().getBotId()
                ) {

                    String send = event.getMessage();
                    if (Constants.config.getCmd().isQqChatPrefixEnable()) {
                        var split = event.getMessage().split(" ");
                        if (Constants.config.getCmd().getQqChatPrefix().equals(split[0])) //指定前缀发送
                            send = split[1];
                        else return;
                    }
                    String toSend = String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", Constants.config.getCmd().getGuildPrefix(), event.getChannelId(), event.getSender().getNickname(), send);
                    TickEvents.getToSendQueue().add(toSend);

                }
            }
        });
    }

    private static void GuildCmdsHandler(EventDispatchers dispatchers) {
        dispatchers.addListener(new SimpleListener<GuildMessageEvent>() {
            @Override
            public void onMessage(GuildMessageEvent event) {
                if (Constants.config.getCommon().getChannelIdList().contains(event.getChannelId())
                        && event.getMessage().startsWith(Constants.config.getCmd().getCommandStart())//命令前缀
                        && Constants.config.getStatus().isRECEIVE_ENABLED()//总接受开关
                        && Constants.config.getStatus().isR_COMMAND_ENABLED()//接受命令开关
                ) {
                    CmdApi.invokeCommandGuild(event);
                }
            }
        });
    }
}
