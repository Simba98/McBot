package cn.evolvefield.mods.botapi.model.action.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Created on 2022/7/8.
 *
 * @author cnlimiter
 */
@Data
public class CheckUrlSafelyResp {

    @JSONField(name = "level")
    private int level;

}
