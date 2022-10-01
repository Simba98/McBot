package cn.evolvefield.mods.botapi.sdk.model.event.message;

import cn.evolvefield.mods.botapi.sdk.model.bean.MsgChainBean;
import cn.evolvefield.mods.botapi.sdk.model.event.Event;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author cnlimiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class MessageEvent extends Event {

    @SerializedName( "message_type")
    private String messageType;

    @SerializedName( "user_id")
    private long userId;

    @SerializedName( "message")
    private String message;

    @SerializedName( "raw_message")
    private String rawMessage;

    @SerializedName( "font")
    private int font;

    private List<MsgChainBean> arrayMsg;

}
