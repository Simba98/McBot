package cn.evolvefield.mods.botapi.impl.init.config;

import cn.evolvefield.mods.botapi.sdk.config.BotConfig;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 13:44
 * Version: 1.0
 */
@Data
public class ModConfig {
    public String getConfigName() {
        return "botapi";
    }


    @SerializedName("status")
    private Status status = new Status();
    @SerializedName("cmd")
    private Cmd cmd = new Cmd();
    @SerializedName("cmd")
    private BotConfig botConfig = new BotConfig();
    @Data
    public static class Status {
        @SerializedName("RECEIVE_ENABLED")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("R_COMMAND_ENABLED")
        private boolean R_COMMAND_ENABLED = true;
        @SerializedName("R_CHAT_ENABLE")
        private boolean R_CHAT_ENABLE = true;
        @SerializedName("SEND_ENABLED")
        private boolean SEND_ENABLED = true;
        @SerializedName("WELCOME_ENABLE")
        private boolean S_WELCOME_ENABLE = true;
        @SerializedName("JOIN_ENABLE")
        private boolean S_JOIN_ENABLE = true;
        @SerializedName("LEAVE_ENABLE")
        private boolean S_LEAVE_ENABLE = true;
        @SerializedName("DEATH_ENABLE")
        private boolean S_DEATH_ENABLE = true;
        @SerializedName("S_CHAT_ENABLE")
        private boolean S_CHAT_ENABLE = true;
        @SerializedName("S_ADVANCE_ENABLE")
        private boolean S_ADVANCE_ENABLE = true;
    }

    @Data
    public static class Cmd {
        @SerializedName("welcome_notice")
        private String welcomeNotice = "欢迎加群~";
        @SerializedName("leave_notice")
        private String leaveNotice = "有人离开了我们qwq";
        @SerializedName("command_start")
        private String commandStart = "!";
        @SerializedName("bind_command")
        private String bindCommand = "bind";
        @SerializedName("whitelist_command")
        private String whiteListCommand = "white";
        @SerializedName("bind_success")
        private String bindSuccess =
                "绑定成功 ┈━═☆\n" +
                        "成功绑定账号: %Player%\n" +
                        "你他妈绑定成功了呢~\"";
        @SerializedName("bindFail")
        private String bindFail =
                "绑定失败 ┈━═☆\n" +
                        "你的QQ已经绑定或 %Player% 已被绑定\n" +
                        "你他妈不能再绑定了呢~";
        @SerializedName("bindNotOnline")
        private String bindNotOnline =
                "玩家不在线 ┈━═☆\n" +
                        "%Player% 不在线或者不存在哦\n" +
                        "还他妈不上线搁这玩QQ呢~";

        @SerializedName("qqPrefix")
        private String qqPrefix = "MC";
    }

    @Data
    public static class Common {
        @SerializedName("frame")
        private String frame = "cqhttp";//go-cqhttp///mirai
        @SerializedName("group_on")
        private boolean groupOn = true;
        @SerializedName("group_id_list")
        private List<Long> groupIdList = new ArrayList<>();
        @SerializedName("guild_on")
        private boolean guildOn = false;
        @SerializedName("guild_id")
        private String guildId = "";
        @SerializedName("channel_id_list")
        private List<String> channelIdList = new ArrayList<>();
        @SerializedName("bot_id")
        private long botId = 0;
        @SerializedName("master_id")
        private long masterId = 0;
        @SerializedName("ws_common")
        private String wsCommon = "ws://127.0.0.1:8080";
        @SerializedName("ws_Key")
        private String wsKey = "";
        @SerializedName("enable")
        private boolean enable = true;
        @SerializedName("debuggable")
        private boolean debuggable = false;
        @SerializedName("whitelist_enable")
        private boolean WHITELIST_ENABLED = false;
        @SerializedName("language_select")
        private String languageSelect = "zh_cn";
        @SerializedName("auto_open")
        private boolean autoOpen = true;
        @SerializedName("reconnect")
        private boolean reconnect = true;
    }

}
