package cn.evolvefield.mods.botapi.core.action;

import lombok.Getter;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/8 1:09
 * Description:
 */
@Getter
public enum ActionPath {

    SEND_MSG("send_msg"),
    SEND_CMD("send_cmd");
    /**
     * 请求路径
     */
    private final String path;

    /**
     * 枚举构造函数
     *
     * @param path 请求路径
     */
    ActionPath(String path) {
        this.path = path;
    }

    /**
     * 获取请求路径
     *
     * @return 请求路径
     */
    public String getPath() {
        return this.path;
    }
}
