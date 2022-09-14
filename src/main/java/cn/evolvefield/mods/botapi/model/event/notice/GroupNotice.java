package cn.evolvefield.mods.botapi.model.event.notice;

import cn.evolvefield.mods.botapi.model.event.global.Notice;

/**
 * Description: 群通知通用类
 * Author: cnlimiter
 * Date: 2022/9/14 16:44
 * Version: 1.0
 */
public class GroupNotice extends Notice {
    private Long groupId;//群号
    private Long userId;//QQ号

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GroupNotice{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
