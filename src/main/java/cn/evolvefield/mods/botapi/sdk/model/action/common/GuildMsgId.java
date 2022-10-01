package cn.evolvefield.mods.botapi.sdk.model.action.common;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:39
 * Version: 1.0
 */
@Data
public class GuildMsgId {
    @SerializedName( "message_id")
    private String messageId;

}
