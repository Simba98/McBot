package cn.evolvefield.mods.botapi.listener;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 15:53
 * Version: 1.0
 */
public interface Handler<T> {
    void handle(T t);
}
