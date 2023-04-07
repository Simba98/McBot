package cn.evolvefield.mods.botapi.core.dto;


import cn.evolvefield.mods.botapi.core.ActionData;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/10 15:02
 * Version: 1.0
 */
public class ActionSuccess extends ActionData<Object> {
    public ActionSuccess(){
        this.setStatus("ok");
        this.setRetCode(0);
        this.setData(null);
    }
}
