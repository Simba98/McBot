package cn.evolvefield.mods.botapi.model.action.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author cnlimiter
 */
@Data
public class WordSlicesResp {

    @JSONField(name = "slices")
    private List<String> slices;

}
