package cn.evolvefield.mods.botapi.model.event.message;

import cn.evolvefield.mods.botapi.model.event.global.RecvMessage;

/**
 * Description: 私聊消息
 * Author: cnlimiter
 * Date: 2022/9/14 16:33
 * Version: 1.0
 */
public class PrivateMessage extends RecvMessage {
    private Integer tempSource;//临时会话来源
    private PrivateSender privateSender;

    public Integer getTempSource() {
        return tempSource;
    }

    public void setTempSource(Integer tempSource) {
        this.tempSource = tempSource;
    }

    public PrivateSender getSender() {
        return privateSender;
    }

    public void setSender(PrivateSender privateSender) {
        this.privateSender = privateSender;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "tempSource=" + tempSource +
                ", sender=" + privateSender +
                "} " + super.toString();
    }

    public static class PrivateSender {
        private Long userId;//QQ号
        private String nickname;//昵称/备注
        private String sex;//性别
        private Integer age;//年龄

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Sender{" +
                    "userId=" + userId +
                    ", nickname='" + nickname + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
