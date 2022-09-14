package cn.evolvefield.mods.botapi.model.event.notice;

import cn.evolvefield.mods.botapi.model.event.global.Notice;

/**
 * Description:新增好友
 * Author: cnlimiter
 * Date: 2022/9/14 16:57
 * Version: 1.0
 */
public class FriendAddNotice extends Notice {
    private Long userId;//新添加好友 QQ 号

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FriendAddNotice{" +
                "userId=" + userId +
                "} " + super.toString();
    }

}
