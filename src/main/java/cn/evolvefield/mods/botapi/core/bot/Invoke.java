package cn.evolvefield.mods.botapi.core.bot;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.cmd.CustomCmdRun;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.events.ChannelGroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.init.handler.CustomCmdHandler;
import com.google.common.collect.Lists;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Invoke {

    public static void invokeChannelCmd(ChannelGroupMessageEvent event) {
        String message = "";
        String bindCommand = BotApi.config.getCmd().getBindCommand();
        String whiteListCommand = BotApi.config.getCmd().getWhiteListCommand();
        if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
            message = event.getMessage();
            String[] formatMsg = message.split(" ");
            String commandBody = formatMsg[0].substring(1);
//            if (!event.getRole().equals("MEMBER") || !event.getRole().equals("member")) {
//                CustomCmdHandler.getInstance().getCustomCmds().stream()
//                        .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandBody))
//                        .forEach(customCmd -> SendMessage.GroupCmd(customCmd, event));
//            }
//            CustomCmdHandler.getInstance().getCustomCmds().stream()
//                    .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandBody))
//                    .forEach(customCmd -> SendMessage.GroupCmd(customCmd, event));

        }
    }
    public static void invokeCommand(GroupMessageEvent event) {
        String message = "";
        String bindCommand = BotApi.config.getCmd().getBindCommand();


        if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
            message = event.getMessage();
            invokeCommandMain(message, event);

        }else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
            message = event.getMiraiMessage().get(1).getMessage();
            invokeCommandMain(message, event);
        }

    }

    private static void invokeCommandMain(String message, GroupMessageEvent event){
        String[] formatMsg = message.split(" ");
        String commandBody = formatMsg[0].substring(1);

        if (!event.getRole().equals("MEMBER") || !event.getRole().equals("member")) {
            CustomCmdHandler.getInstance().getCustomCmds().stream()
                    .filter(customCmd -> customCmd.getRequirePermission() == 1 && customCmd.getCmdAlies().equals(commandBody))
                    .forEach(customCmd -> SendMessage.GroupCmd(customCmd, event));
        }
        CustomCmdHandler.getInstance().getCustomCmds().stream()
                .filter(customCmd -> customCmd.getRequirePermission() == 0 && customCmd.getCmdAlies().equals(commandBody))
                .forEach(customCmd -> SendMessage.GroupCmd(customCmd, event));

    }

    public static void invokeCommandOld(GroupMessageEvent event) {
        String message = "";
        String bindCommand = BotApi.config.getCmd().getBindCommand();
        String whiteListCommand = BotApi.config.getCmd().getWhiteListCommand();

        if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
            message = event.getMessage();
            String[] formatMsg = message.split(" ");
            String commandBody = formatMsg[0].substring(1);

            if (!event.getRole().equals("member")) {
                masterMsgParse(whiteListCommand, formatMsg, commandBody);
            }
            memberMsgParse(event, bindCommand, commandBody, formatMsg);

        } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
            message = event.getMiraiMessage().get(1).getMessage();
            String[] formatMsg = message.split(" ");
            String commandBody = formatMsg[0].substring(1);

            if (!event.getPermission().equals("MEMBER")) {
                masterMsgParse(whiteListCommand, formatMsg, commandBody);
            }
            memberMsgParse(event, bindCommand, commandBody, formatMsg);

        }

    }

    private static void memberMsgParse(GroupMessageEvent event, String bindCommand, String commandBody, String[] formatMsg) {
        if (commandBody.equals("tps")) {
            SendMessage.Group(event.getGroupId(), tpsCmd());
        } else if (commandBody.equals("list")) {

            SendMessage.Group(event.getGroupId(), listCmd());
        } else if (commandBody.startsWith(bindCommand)) {
            if (formatMsg.length == 1) {
                SendMessage.Group(event.getGroupId(), "请输入有效的游戏名");
                return;
            }

            String BindPlay = formatMsg[1];
            List<String> msg = new ArrayList<>();


            if (BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay) == null) {
                String m = BotApi.config.getCmd().getBindNotOnline();
                msg.add(m.replace("%Player%", BindPlay));
                SendMessage.Group(event.getGroupId(), msg);
                return;
            }

            if (BindApi.addGroupBind(event.getUserId(), BindPlay)) {
                String m = BotApi.config.getCmd().getBindSuccess();
                msg.add(m.replace("%Player%", BindPlay));

            } else {
                String m = BotApi.config.getCmd().getBindFail();
                msg.add(m.replace("%Player%", BindPlay));
            }

            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("处理命令bind:" + msg + "PlayerName:" + BindPlay);
            }

            SendMessage.Group(event.getGroupId(), msg);

        }
    }

    private static void masterMsgParse(String whiteListCommand, String[] formatMsg, String commandBody) {
        if (commandBody.startsWith(whiteListCommand)) {
            String subCmd = formatMsg[1];
            switch (subCmd) {
                case "add" -> {
                    String playerName = formatMsg[2];
                    int success = BotApi.SERVER.getCommands().performPrefixedCommand(CustomCmdRun.CUSTOM, "whitelist add " + playerName);

                    if (success == 0) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单失败或已经添加了白名单！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单成功！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white add " + playerName);
                    }
                }
                case "del" -> {
                    String playerName = formatMsg[2];
                    int success = BotApi.SERVER.getCommands().performPrefixedCommand(CustomCmdRun.CUSTOM, "whitelist remove " + playerName);

                    if (success == 0) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "失败或已经从白名单移除！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "成功！");
                    }
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white del " + playerName);
                    }
                }
                case "list" -> {
                    String[] strings = BotApi.SERVER.getPlayerList().getWhiteListNames();
                    List<String> msg = new ArrayList<>();
                    msg.add("当前服务器白名单：\n");
                    msg.addAll(Arrays.asList(strings));
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white list");
                    }
                }
                case "on" -> {
                    PlayerList playerList = BotApi.SERVER.getPlayerList();
                    if (playerList.isUsingWhitelist()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "已经打开了白名单！哼~");
                    } else {
                        playerList.setUsingWhiteList(true);
                        BotApi.SERVER.setEnforceWhitelist(true);
                        kickUnlistedPlayers();
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "打开白名单成功！");

                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white on");
                    }
                }
                case "off" -> {
                    PlayerList playerList = BotApi.SERVER.getPlayerList();
                    if (!playerList.isUsingWhitelist()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "白名单早就关了！");
                    } else {
                        playerList.setUsingWhiteList(false);
                        BotApi.SERVER.setEnforceWhitelist(false);
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "关闭白名单成功！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white off");
                    }
                }
                case "reload" -> {
                    BotApi.SERVER.getPlayerList().reloadWhiteList();
                    kickUnlistedPlayers();
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), "刷新白名单成功！");
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white reload");
                    }
                }
            }
        }
    }

    public static void kickUnlistedPlayers() {
        if (BotApi.SERVER.isEnforceWhitelist()) {
            PlayerList playerList = BotApi.SERVER.getPlayerList();
            UserWhiteList userWhiteList = playerList.getWhiteList();
            List<ServerPlayer> list = Lists.newArrayList(playerList.getPlayers());

            for (ServerPlayer serverPlayer : list) {
                if (!userWhiteList.isWhiteListed(serverPlayer.getGameProfile())) {
                    serverPlayer.connection.disconnect(Component.translatable("multiplayer.disconnect.not_whitelisted"));
                }
            }

        }
    }

    private static String tpsCmd() {
        double overTickTime = mean(BotApi.SERVER.getTickTime(Level.OVERWORLD)) * 1.0E-6D;
        double overTPS = Math.min(1000.0 / overTickTime, 20);
        double netherTickTime = mean(BotApi.SERVER.getTickTime(Level.NETHER)) * 1.0E-6D;
        double netherTPS = Math.min(1000.0 / netherTickTime, 20);
        double endTickTime = mean(BotApi.SERVER.getTickTime(Level.END)) * 1.0E-6D;
        double endTPS = Math.min(1000.0 / endTickTime, 20);

        String outPut = String.format("主世界 TPS: %.2f", overTPS)
                + "\n" + String.format("下界 TPS: %.2f", netherTPS)
                + "\n" + String.format("末地 TPS: %.2f", endTPS);


        if (BotApi.config.getCommon().isDebuggable()) {
            BotApi.LOGGER.info("处理命令tps:" + outPut);
        }
        return outPut;
    }

    private static String listCmd() {
        List<ServerPlayer> users = BotApi.SERVER.getPlayerList().getPlayers();

        String result = "在线玩家数量: " + users.size();

        if (users.size() > 0) {
            Component userList = users.stream()
                    .map(Player::getDisplayName)
                    .reduce(Component.literal(""), (listString, user) ->
                            listString.getString().length() == 0 ? user : Component.literal(listString.getString() + ", " + user.getString())
                    );
            result += "\n" + "玩家列表: " + userList.getString();
        }
        if (BotApi.config.getCommon().isDebuggable()) {
            BotApi.LOGGER.info("处理命令list:" + result);
        }
        return result;
    }


    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, Long::sum);

        return sum / values.length;
    }

}
