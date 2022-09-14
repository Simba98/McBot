package cn.evolvefield.mods.botapi.model.action.common;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 9:12
 * Version: 1.0
 */
@Data
public class Anonymous {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("flag")
    private String flag;
}
