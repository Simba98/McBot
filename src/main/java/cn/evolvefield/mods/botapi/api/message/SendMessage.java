package cn.evolvefield.mods.botapi.api.message;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CustomCmd;
import cn.evolvefield.mods.botapi.api.cmd.CustomCmdRun;
import cn.evolvefield.mods.botapi.api.events.ChannelGroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import cn.evolvefield.mods.botapi.util.MsgUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class SendMessage {

    public static void ChannelGroup(String guild_id, String channel_id, String message) {
        if (BotApi.config.getCommon().isEnable()
                && BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")
                && BotApi.config.getCommon().isGuildOn()) {
            CompletableFuture.runAsync(() -> {
                //异步，防止同时发送群消息和频道消息时出现卡顿
                JsonObject data = new JsonObject();
                JsonObject params = new JsonObject();
                data.addProperty("action", "send_guild_channel_msg");
                params.addProperty("guild_id", guild_id);
                params.addProperty("channel_id", channel_id);
                params.addProperty("message", message);
                data.add("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向频道：" + guild_id + "的子频道：" + channel_id + "发送消息" + message);
                }

            });


        }
    }

    public static void ChannelGroup(String guild_id, String channel_id, List<String> message) {
        if (BotApi.config.getCommon().isEnable()
                && BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")
                && BotApi.config.getCommon().isGuildOn()) {
            CompletableFuture.runAsync(() -> {

                JsonObject data = new JsonObject();
                JsonObject params = new JsonObject();
                data.addProperty("action", "send_guild_channel_msg");
                params.addProperty("guild_id", guild_id);
                params.addProperty("channel_id", channel_id);
                params.addProperty("message", MsgUtil.setListMessage(message));
                data.add("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向频道：" + guild_id + "的子频道：" + channel_id + "发送消息" + message);
                }

            });
        }
    }

    public static void Temp(long user_id, long group_id, String message) {
        if (BotApi.config.getCommon().isEnable()) {
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.addProperty("action", "send_private_msg");
                params.addProperty("group_id", group_id);
                params.addProperty("user_id", user_id);
                params.addProperty("message", message);
                data.add("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群：" + group_id + "的用户：" + user_id + "发送临时消息" + message);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.addProperty("sessionKey", BotData.getSessionKey());
                data.addProperty("qq", user_id);
                data.addProperty("group", group_id);
                data.add("messageChain", MsgUtil.getMessage(message));

                JsonObject main = new JsonObject();
                main.addProperty("syncId", "");
                main.addProperty("command", "sendTempMessage");
                main.add("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群：" + group_id + "的用户：" + user_id + "发送临时消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }
        }

    }

    public static void Group(long group_id, String message) {
        if (BotApi.config.getCommon().isEnable()) {
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.addProperty("action", "send_group_msg");
                params.addProperty("group_id", group_id);
                params.addProperty("message", message);
                data.add("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + message);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.addProperty("sessionKey", BotData.getSessionKey());
                data.addProperty("target", group_id);
                data.add("messageChain", MsgUtil.getMessage(message));

                JsonObject main = new JsonObject();
                main.addProperty("syncId", "");
                main.addProperty("command", "sendGroupMessage");
                main.add("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }

        }
    }

    public static void Group(long group_id, List<String> message) {
        if (BotApi.config.getCommon().isEnable()) {
            JsonObject data = new JsonObject();
            JsonObject params = new JsonObject();
            JsonArray array = new JsonArray();
            if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("cqhttp")) {
                data.addProperty("action", "send_group_msg");
                params.addProperty("group_id", group_id);
                params.addProperty("message", MsgUtil.setListMessage(message));
                data.add("params", params);
                WebSocketService.client.send(data.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + data);
                }

            } else if (BotApi.config.getCommon().getFrame().equalsIgnoreCase("mirai")) {

                data.addProperty("sessionKey", BotData.getSessionKey());
                data.addProperty("target", group_id);
                data.add("messageChain", MsgUtil.getMessage(message));

                JsonObject main = new JsonObject();
                main.addProperty("syncId", "");
                main.addProperty("command", "sendGroupMessage");
                main.add("content", data);

                WebSocketService.client.send(main.toString());
                if (BotApi.config.getCommon().isDebuggable()) {
                    BotApi.LOGGER.info("向群" + group_id + "发送消息" + main);
                }
            } else {
                BotApi.LOGGER.warn("§c未找到机器人框架.");
            }
        }

    }



    private static StringBuilder CmdMain(CustomCmd customCmd){
        StringBuilder result = new StringBuilder();
        BotApi.SERVER.getCommands().performPrefixedCommand(CustomCmdRun.CUSTOM, customCmd.getCmdContent());

        for (String s : CustomCmdRun.CUSTOM.outPut) {
            result.append(s.replaceAll("§\\S", "")).append("\n");
        }
        CustomCmdRun.CUSTOM.outPut.clear();
        return result;
    }

    public static void GroupCmd(CustomCmd customCmd, GroupMessageEvent event) {
        SendMessage.Group(event.getGroupId(), CmdMain(customCmd).toString());
    }

    public static void ChannelCmd(CustomCmd customCmd, ChannelGroupMessageEvent event) {
        CompletableFuture.runAsync(() -> {
            SendMessage.ChannelGroup(event.getGuild_id(), event.getChannel_id(), CmdMain(customCmd).toString());
        });
    }

}
