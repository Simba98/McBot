package cn.evolvefield.mods.botapi.model.event.message;

import cn.evolvefield.mods.botapi.model.event.global.RecvMessage;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 16:48
 * Version: 1.0
 */
public class GuildMessage extends RecvMessage {

    private String guildId;

    private String channelId;

    private String selfTinyId;

    private GuildSender guildSender;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSelfTinyId() {
        return selfTinyId;
    }

    public void setSelfTinyId(String selfTinyId) {
        this.selfTinyId = selfTinyId;
    }

    public GuildSender getGuildSender() {
        return guildSender;
    }

    public void setGuildSender(GuildSender guildSender) {
        this.guildSender = guildSender;
    }



    public static class GuildSender {

        private long userId;

        private String tinyId;

        private String nickname;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getTinyId() {
            return tinyId;
        }

        public void setTinyId(String tinyId) {
            this.tinyId = tinyId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "GuildSender{" +
                    "userId=" + userId +
                    ", tinyId='" + tinyId + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GuildMessage{" +
                "guildId='" + guildId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", selfTinyId='" + selfTinyId + '\'' +
                ", guildSender=" + guildSender +
                '}';
    }
}
