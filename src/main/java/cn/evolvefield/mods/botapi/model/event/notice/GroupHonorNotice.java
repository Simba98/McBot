package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:群成员荣誉变更提示
 * Author: cnlimiter
 * Date: 2022/9/14 17:07
 * Version: 1.0
 */
public class GroupHonorNotice extends GroupNotice {
    private String subType;//提示类型 honor
    private String honorType;//talkative:龙王 performer:群聊之火 emotion:快乐源泉

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getHonorType() {
        return honorType;
    }

    public void setHonorType(String honorType) {
        this.honorType = honorType;
    }

    @Override
    public String toString() {
        return "GroupHonorNotice{" +
                "subType='" + subType + '\'' +
                ", honorType='" + honorType + '\'' +
                "} " + super.toString();
    }
}
