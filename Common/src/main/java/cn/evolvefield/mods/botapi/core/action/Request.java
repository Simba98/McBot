package cn.evolvefield.mods.botapi.core.action;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.annotation.Nullable;
import blue.endless.jankson.annotation.SerializedName;
import cn.evolvefield.mods.botapi.util.JsonsObject;
import lombok.Data;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/7 22:57
 * Description:动作请求
 */
@Data
public class Request{
    @SerializedName("action")
    private ActionPath action;
    @SerializedName("params")
    private Param params;
    @Nullable
    @SerializedName("echo")
    private String echo;

    /**
     * Project: BotConnect-dev
     * Author: cnlimiter
     * Date: 2023/4/7 23:01
     * Description:动作参数
     */
    @Data
    public static class Param<T> {

        /**
         * 存在 qq,guild,kook,tel,discord,ding_ding
         */
        @SerializedName("platform")
        private String platform;

        /**
         * 存在 private, public
         */
        @SerializedName("detail_type")
        private String detailType;


        @SerializedName("data")
        private JsonsObject data;
    }



}
