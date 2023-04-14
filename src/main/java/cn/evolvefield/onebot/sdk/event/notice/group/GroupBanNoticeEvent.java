package cn.evolvefield.onebot.sdk.event.notice.group;

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
public class GroupBanNoticeEvent extends NoticeEvent {

    @SerializedName( "sub_type")
    private String subType;

    @SerializedName( "group_id")
    private long groupId;

    @SerializedName( "operator_id")
    private long operatorId;

    @SerializedName( "duration")
    private long duration;

    @SerializedName( "user_id")
    private long userId;

}
