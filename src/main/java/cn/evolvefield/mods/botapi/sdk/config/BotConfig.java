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
@AllArgsConstructor
@NoArgsConstructor
public class BotConfig {
    @Expose
    private String url;//websocket地址
    @Expose
    private String token;//token鉴权
    @Expose
    private Boolean isAccessToken;//是否开启鉴权

    public BotConfig(String url) {
        this(url, null);
    }

    public BotConfig(String url, String token) {
        this(url, token, false);
    }

}
