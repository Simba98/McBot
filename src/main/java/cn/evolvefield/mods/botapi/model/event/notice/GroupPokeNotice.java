package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:群内戳一戳
 * Author: cnlimiter
 * Date: 2022/9/14 16:56
 * Version: 1.0
 */
public class GroupPokeNotice extends GroupNotice {
    private String subType;//poke	提示类型
    private Long targetId;//被戳者 QQ 号

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
        return "GroupPokeNotice{" +
                "subType='" + subType + '\'' +
                ", targetId=" + targetId +
                "} " + super.toString();
    }
}
