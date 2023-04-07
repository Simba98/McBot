package cn.evolvefield.mods.botapi.core.action;

import blue.endless.jankson.annotation.Nullable;
import blue.endless.jankson.annotation.SerializedName;
import lombok.Data;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/7 22:57
 * Description:动作响应
 */
@Data
public class Response<T>{
    @SerializedName("status")
    private String status;
    @SerializedName("retcode")
    private int retCode;
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @Nullable
    @SerializedName("echo")
    private String echo;
}
