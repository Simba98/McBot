package cn.evolvefield.mods.botapi.core.action;


import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/7 22:57
 * Description:动作请求
 */
@Data
public class Request {
    @SerializedName("action")
    private String action;
    @SerializedName("params")
    private Param params;
    @SerializedName("echo")
    private String echo;

    /**
     * Project: BotConnect-dev
     * Author: cnlimiter
     * Date: 2023/4/7 23:01
     * Description:动作参数
     */
    @Data
    public static class Param{

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
        private JsonObject data;


        @Override
        public String toString() {
            return "Params{" +
                    "platform=" + platform +
                    ", detailType=" + detailType +
                    ", data=" + data +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "Request{" +
                "action=" + action +
                ", params=" + params +
                ", echo=" + echo +
                '}';
    }

}
