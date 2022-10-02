package cn.evolvefield.mods.botapi.sdk.config;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/1 17:05
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotConfig {
    @Expose
    private String url = "ws://127.0.0.1:8080";//websocket地址
    @Expose
    private String token = "";//token或者verifyKey鉴权
    @Expose
    private long botId = 0;
    @Expose
    private Boolean isAccessToken = false;//是否开启鉴权
    @Expose
    private Boolean mirai = false;//是否开启mirai


}
