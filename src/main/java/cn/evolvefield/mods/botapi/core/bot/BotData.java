package cn.evolvefield.mods.botapi.core.bot;

import java.util.List;

/**
 * Description:持久层数据
 * Author: cnlimiter
 * Date: 2022/3/18 18:59
 * Version: 1.0
 */
public class BotData {
    private static String ws;
    private static String BotFrame;
    private static long QQId;
    private static long GroupId;
    private static String guildId;
    private static List<String> channelIdList;
    private static String VerifyKey;
    private static String SessionKey;

    public BotData() {
    }

    public static String getBotFrame() {
        return BotFrame;
    }

    public static void setBotFrame(String botFrame) {
        BotFrame = botFrame;
    }

    public static String getSessionKey() {
        return SessionKey;
    }

    public static void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public static long getGroupId() {
        return GroupId;
    }

    public static void setGroupId(long groupId) {
        GroupId = groupId;
    }

    public static String getGuildId() {
        return guildId;
    }

    public static void setGuildId(String guildId) {
        BotData.guildId = guildId;
    }

    public static List<String> getChannelIdList() {
        return channelIdList;
    }

    public static void setChannelIdList(List<String> channelIdList) {
        BotData.channelIdList = channelIdList;
    }

    public static void addChannelId(String channelId) {
        channelIdList.add(channelId);
    }

    public static long getQQId() {
        return QQId;
    }

    public static void setQQId(long QQId) {
        BotData.QQId = QQId;
    }

    public static String getVerifyKey() {
        return VerifyKey;
    }

    public static void setVerifyKey(String verifyKey) {
        VerifyKey = verifyKey;
    }

    public static String getWs() {
        return ws;
    }

    public static void setWs(String ws) {
        BotData.ws = ws;
    }
}
