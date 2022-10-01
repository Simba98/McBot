package cn.evolvefield.mods.botapi.sdk.model.event.notice;

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
public class GroupCardChangeNoticeEvent extends NoticeEvent {

    @SerializedName( "card_new")
    private String cardNew;

    @SerializedName( "group_id")
    private long groupId;

    @SerializedName( "card_old")
    private String cardOld;

    @SerializedName( "user_id")
    private long userId;

}
