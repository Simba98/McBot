package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:群禁言
 * Author: cnlimiter
 * Date: 2022/9/14 17:06
 * Version: 1.0
 */
public class GroupBanNotice extends GroupNotice {
    private String subType;//approve管理员同意 invite管理员邀请
    private Long operatorId;//操作者 QQ 号
    private Long duration;//禁言时长, 单位秒

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GroupBanNotice{" +
                "subType='" + subType + '\'' +
                ", operatorId=" + operatorId +
                ", duration=" + duration +
                "} " + super.toString();
    }
}

