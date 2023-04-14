package cn.evolvefield.onebot.sdk.event.notice.friend;

import cn.evolvefield.onebot.sdk.event.notice.NoticeEvent;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Created on 2022/7/8.
 *
 * @author cnlimiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PrivateMsgDeleteNoticeEvent extends NoticeEvent {

    @SerializedName("user_id")
    private long userId;

    @SerializedName("message_id")
    private long msgId;

}
