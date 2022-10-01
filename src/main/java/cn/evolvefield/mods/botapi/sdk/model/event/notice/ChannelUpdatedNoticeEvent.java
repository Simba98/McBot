package cn.evolvefield.mods.botapi.sdk.model.event.notice;

import cn.evolvefield.mods.botapi.sdk.model.action.response.ChannelInfoResp;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author cnlimiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ChannelUpdatedNoticeEvent extends NoticeEvent {

    /**
     * 频道ID
     */
    @SerializedName( "guild_id")
    private String guildId;

    /**
     * 子频道ID
     */
    @SerializedName( "channel_id")
    private String channelId;

    /**
     * 操作者ID
     */
    @SerializedName( "operator_id")
    private String operatorId;

    /**
     * 更新前的频道信息
     */
    @SerializedName( "old_info")
    private ChannelInfoResp oldInfo;

    /**
     * 更新后的频道信息
     */
    @SerializedName( "new_info")
    private ChannelInfoResp newInfo;

}
