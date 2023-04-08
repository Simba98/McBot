package cn.evolvefield.mods.botapi.init.config;

import blue.endless.jankson.Comment;
import blue.endless.jankson.annotation.SerializedName;
import lombok.Data;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/5 19:43
 * Description:
 */
@Data
public class BotConfig {


    @SerializedName("host")
    @Comment("服务端地址")
    private String host = "127.0.0.1";
    @SerializedName("port")
    @Comment("服务端端口")
    private int port = 6666;
    @SerializedName("access_token")
    @Comment("密钥")
    private String access_token= "";
}
