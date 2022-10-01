package cn.evolvefield.mods.botapi.sdk.model.event.request;

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
public class GroupAddRequestEvent extends RequestEvent {

    @SerializedName( "sub_type")
    private String subType;

    @SerializedName( "group_id")
    private long groupId;

}
