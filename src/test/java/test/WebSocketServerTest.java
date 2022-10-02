package test;

import cn.evolvefield.mods.botapi.sdk.config.BotConfig;
import cn.evolvefield.mods.botapi.sdk.connection.ConnectFactory;
import cn.evolvefield.mods.botapi.sdk.connection.Connection;
import cn.evolvefield.mods.botapi.sdk.listener.Handler;
import cn.evolvefield.mods.botapi.sdk.listener.SimpleListener;
import cn.evolvefield.mods.botapi.sdk.listener.impl.GroupMessageListener;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.mods.botapi.sdk.model.event.message.PrivateMessageEvent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/23 13:19
 * Version: 1.0
 */
public class WebSocketServerTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        Connection service = ConnectFactory.createWebsocketClient(new BotConfig("ws://127.0.0.1:8080"),blockingQueue);
        EventDispatchers dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        GroupMessageListener groupMessageListener = new GroupMessageListener();
        groupMessageListener.addHandler("天气", new Handler<GroupMessageEvent>() {
            @Override
            public void handle(GroupMessageEvent groupMessage) {
                System.out.println(groupMessage);

            }
        });
        dispatchers.addListener(groupMessageListener);
        dispatchers.addListener(new SimpleListener<PrivateMessageEvent>() {//私聊监听
            @Override
            public void onMessage(PrivateMessageEvent privateMessage) {
                System.out.println(privateMessage);
            }
        });

        dispatchers.start(10);//线程组处理任务

    }
}
