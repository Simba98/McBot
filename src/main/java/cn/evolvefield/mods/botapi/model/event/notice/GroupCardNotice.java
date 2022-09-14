package cn.evolvefield.mods.botapi.model.event.notice;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:07
 * Version: 1.0
 */
public class GroupCardNotice extends GroupNotice {
    private Long cardNew;//新名片
    private Long cardOld;//旧名片

    public Long getCardNew() {
        return cardNew;
    }

    public void setCardNew(Long cardNew) {
        this.cardNew = cardNew;
    }

    public Long getCardOld() {
        return cardOld;
    }

    public void setCardOld(Long cardOld) {
        this.cardOld = cardOld;
    }

    @Override
    public String toString() {
        return "GroupCardNotice{" +
                "cardNew=" + cardNew +
                ", cardOld=" + cardOld +
                "} " + super.toString();
    }
}

