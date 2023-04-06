package cn.evolvefield.mods.botapi.core;

import blue.endless.jankson.annotation.SerializedName;
import lombok.Data;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/6 20:19
 * Description:
 */
@Data
public class ActionData<T>{
    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private int retCode;
    @SerializedName("data")
    private T data;
    @SerializedName("echo")
    private String echo;
}
