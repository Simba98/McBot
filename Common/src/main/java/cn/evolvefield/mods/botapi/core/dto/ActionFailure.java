package cn.evolvefield.mods.botapi.core.dto;


import cn.evolvefield.mods.botapi.core.action.Response;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/9 23:26
 * Version: 1.0
 */
public class ActionFailure extends Response<String> {
    public ActionFailure(){
        this.setStatus("failed");
        this.setRetCode(103);
        this.setData(null);
    }
}
