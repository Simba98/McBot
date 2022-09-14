package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:群红包运气王提示
 * Author: cnlimiter
 * Date: 2022/9/14 17:07
 * Version: 1.0
 */
public class GroupLuckyKingNotice extends GroupNotice {
    private String subType;//lucky_king	提示类型
    private Long targetId;//运气王id

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "GroupLuckyKingNotice{" +
                "subType='" + subType + '\'' +
                ", targetId=" + targetId +
                "} " + super.toString();
    }
}
