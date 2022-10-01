package cn.evolvefield.mods.botapi.sdk.model.action.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created on 2022/7/8.
 *
 * @author cnlimiter
 */
@Data
public class GroupMemberInfoResp {

    @SerializedName( "group_id")
    private long groupId;

    @SerializedName( "user_id")
    private long userId;

    @SerializedName( "nickname")
    private String nickname;

    @SerializedName( "card")
    private String card;

    @SerializedName( "sex")
    private String sex;

    @SerializedName( "age")
    private int age;

    @SerializedName( "area")
    private String area;

    @SerializedName( "join_time")
    private int joinTime;

    @SerializedName( "last_sent_time")
    private int lastSentTime;

    @SerializedName( "level")
    private String level;

    @SerializedName( "role")
    private String role;

    @SerializedName( "unfriendly")
    private boolean unfriendly;

    @SerializedName( "title")
    private String title;

    @SerializedName( "title_expire_time")
    private long titleExpireTime;

    @SerializedName( "card_changeable")
    private boolean cardChangeable;

}
