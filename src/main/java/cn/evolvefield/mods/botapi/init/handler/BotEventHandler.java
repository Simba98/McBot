package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.events.*;
import cn.evolvefield.mods.botapi.api.message.MiraiMessage;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.bot.Invoke;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 19:03
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BotEventHandler {
    @SubscribeEvent
    public static void GroupEventHandler(GroupMessageEvent event) {

        if (event.getGroupId() == BotApi.config.getCommon().getGroupId()
                && BotApi.config.getStatus().isRECEIVE_ENABLED()) {

            if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("收到群" + event.getGroupId() + "发送消息" + event.getMessage());
                }
                if (event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {

                    Invoke.invokeCommand(event);

                } else if (!event.getMessage().contains("[CQ:") && BotApi.config.getStatus().isR_CHAT_ENABLE()
                        && event.getUserId() != BotApi.config.getCommon().getBotId()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    for (MiraiMessage msg : event.getMiraiMessage()) {
                        msg.deBug();
                    }
                    System.out.println(event.getMiraiMessage().get(1).getText());
                }
                if (event.getMiraiMessage().get(1).getText().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {

                    Invoke.invokeCommand(event);

                } else if (!event.getMiraiMessage().get(1).getText().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_CHAT_ENABLE()
                        && event.getUserId() != BotApi.config.getCommon().getBotId()) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMiraiMessage().get(1).getText());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            } else {
                BotApi.LOGGER.error("§b[群服互联] §c错误");
            }

        }
    }

    @SubscribeEvent
    public static void PrivateEventHandler(PrivateMessageEvent event) {
        if (event.getGroupId() == BotData.getGroupId()) {
            if (BindApi.getGroupBindPlayer(event.getUserId()) != null) {
                String senderName = BindApi.getGroupBindPlayer(event.getUserId());
                if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                    if (event.getMessage().startsWith("@")) {
                        String playerName = event.getMessage().substring(1);
                        String[] ctx = event.getMessage().split(" ");
                        atPlayerInGame(event, senderName, ctx, playerName);

                    }
                } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
                    if (event.getMiraiMessage().get(1).getText().startsWith("@")) {
                        String[] ctx = event.getMiraiMessage().get(1).getText().split(" ");
                        String playerName = ctx[0].substring(1);
                        atPlayerInGame(event, senderName, ctx, playerName);
                    }
                } else {
                    BotApi.LOGGER.error("§b[群服互联] §c错误");
                }
            } else {
                SendMessage.Temp(event.getUserId(), event.getGroupId(), "请你先绑定");
            }

        }
    }

    private static void atPlayerInGame(PrivateMessageEvent event, String senderName, String[] ctx, String playerName) {
        if (ctx.length == 2) {
            MutableComponent textcomponenttranslation = Component.translatable("commands.message.display.incoming",
                    senderName, Component.literal(ctx[1]));

            if (BotApi.SERVER.getPlayerList().getPlayerByName(playerName) != null) {
                Objects.requireNonNull(BotApi.SERVER.getPlayerList().getPlayerByName(playerName)).displayClientMessage(textcomponenttranslation, false);
                SendMessage.Temp(event.getUserId(), event.getGroupId(), "成功发送");
            } else {
                SendMessage.Temp(event.getUserId(), event.getGroupId(), "不存在这个人哦");
            }
        } else {
            SendMessage.Temp(event.getUserId(), event.getGroupId(), "错误");
        }
    }

    @SubscribeEvent
    public static void NoticeEventHandler(NoticeEvent event) {
        if (BotApi.config.getStatus().isS_WELCOME_ENABLE()
                && BotApi.config.getStatus().isSEND_ENABLED()
                && event.getGroup_id() == BotApi.config.getCommon().getGroupId()) {
            if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                if (event.getNoticeType().equals("group_increase")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getWelcomeNotice());
                } else if (event.getNoticeType().equals("group_decrease")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getLeaveNotice());
                }
            } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
                if (event.getNoticeType().equals("MemberJoinEvent")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getWelcomeNotice());
                } else if (event.getNoticeType().equals("MemberLeaveEventQuit")) {
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), BotApi.config.getCmd().getLeaveNotice());
                }
            } else {
                BotApi.LOGGER.error("§b[群服互联] §c错误");
            }
        }
    }

    @SubscribeEvent
    public static void ChannelGroupEventHandler(ChannelGroupMessageEvent event) {

        if (event.getGuild_id().equals(BotApi.config.getCommon().getGuildId())
                && BotApi.config.getCommon().getChannelIdList().contains(event.getChannel_id())
        ) {
            if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("收到频道:" + event.getGuild_id() + "的子频道:" + event.getChannel_id() + "发送消息" + event.getMessage());
                }
                if (event.getMessage().startsWith(BotApi.config.getCmd().getCommandStart())
                        && BotApi.config.getStatus().isR_COMMAND_ENABLED()) {

                    Invoke.invokeChannelCmd(event);

                } else if (!event.getMessage().contains("[CQ:") && BotApi.config.getStatus().isR_CHAT_ENABLE()
                ) {
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickname(), event.getMessage());
                    TickEventHandler.getToSendQueue().add(toSend);
                }
            }
        }
    }

    @SubscribeEvent
    public static void RequestsEventHandler(RequestEvent event) {

    }
}
