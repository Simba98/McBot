package cn.evolvefield.onebot.sdk.response.misc;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created on 2022/7/8.
 *
 * @author cnlimiter
 */
@Data
public class DownloadFileResp {

    @SerializedName("file")
    private String file;

}
