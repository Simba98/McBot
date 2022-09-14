package cn.evolvefield.mods.botapi.model.event.global;

/**
 * Description: 通知类型适用
 * Author: cnlimiter
 * Date: 2022/9/14 16:39
 * Version: 1.0
 */
public class Notice extends Message {
    private String  noticeType;//通知类型

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeType='" + noticeType + '\'' +
                "} " + super.toString();
    }
}
