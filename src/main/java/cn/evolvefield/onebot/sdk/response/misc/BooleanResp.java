package cn.evolvefield.onebot.sdk.response.misc;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created on 2022/7/8.
 *
 * @author cnlimiter
 */
@Data
@AllArgsConstructor
public class BooleanResp {

    @SerializedName("yes")
    private boolean yes;

}
