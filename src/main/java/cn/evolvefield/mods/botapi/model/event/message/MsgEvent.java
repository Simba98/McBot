package cn.evolvefield.mods.botapi.model.event.message;

import cn.evolvefield.mods.botapi.model.bean.MsgChainBean;
import cn.evolvefield.mods.botapi.model.event.BaseEvent;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 9:08
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class MsgEvent extends BaseEvent {

    @SerializedName("message_type")
    private String messageType;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("message")
    private String message;

    @SerializedName("raw_message")
    private String rawMessage;

    @SerializedName("font")
    private int font;

    private List<MsgChainBean> arrayMsg;
}
