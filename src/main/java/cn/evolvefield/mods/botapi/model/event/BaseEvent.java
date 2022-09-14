package cn.evolvefield.mods.botapi.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.minecraftforge.eventbus.api.Event;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 9:04
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEvent extends Event {
    @SerializedName("post_type")
    private String postType;

    @SerializedName("time")
    private long time;

    @SerializedName("self_id")
    private long selfId;

}
