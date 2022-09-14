package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:群成员增加
 * Author: cnlimiter
 * Date: 2022/9/14 16:56
 * Version: 1.0
 */
public class GroupIncreaseNotice extends GroupNotice {
    private String subType;//approve管理员同意 invite管理员邀请
    private Long operatorId;//操作者 QQ 号

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

    @Override
    public String toString() {
        return "GroupIncreaseNotice{" +
                "subType='" + subType + '\'' +
                ", operatorId=" + operatorId +
                "} " + super.toString();
    }
}
