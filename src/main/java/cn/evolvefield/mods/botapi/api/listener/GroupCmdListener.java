package cn.evolvefield.mods.botapi.api.listener;

import cn.evolvefield.mods.botapi.api.cmd.CmdApi;
import cn.evolvefield.mods.botapi.sdk.listener.DefaultHandlerListener;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/10/2 15:58
 * Version: 1.0
 */
public class GroupCmdListener extends DefaultHandlerListener<GroupMessageEvent> {
    @Override
    public void onMessage(GroupMessageEvent event) {
        //处理逻辑
        CmdApi.invokeCommandGroup(event);
    }
}
