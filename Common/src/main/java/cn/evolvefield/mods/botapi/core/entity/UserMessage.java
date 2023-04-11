package cn.evolvefield.mods.botapi.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Project: Bot-Connect-koishi
 * Author: cnlimiter
 * Date: 2023/4/9 23:37
 * Description:
 */
@Data
@AllArgsConstructor
public class UserMessage {
    private String platform;
    private String id;
    private String name;
    private String message;

    @Override
    public String toString() {
        return "UserMessage{" +
                "platform='" + platform + '\'' +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
