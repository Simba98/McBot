package test;

import cn.evolvefield.mods.botapi.sdk.config.BotConfig;
import cn.evolvefield.mods.botapi.sdk.connection.ConnectFactory;
import cn.evolvefield.mods.botapi.sdk.connection.Connection;
import cn.evolvefield.mods.botapi.sdk.connection.ModWebSocketClient;
import cn.evolvefield.mods.botapi.sdk.core.Bot;
import cn.evolvefield.mods.botapi.sdk.listener.Handler;
import cn.evolvefield.mods.botapi.sdk.listener.SimpleListener;
import cn.evolvefield.mods.botapi.sdk.listener.impl.GroupMessageListener;
import cn.evolvefield.mods.botapi.sdk.model.action.response.GroupMemberInfoResp;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.mods.botapi.sdk.model.event.message.PrivateMessageEvent;
import cn.evolvefield.mods.botapi.sdk.util.MsgUtils;

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
        ModWebSocketClient service = ConnectFactory.createWebsocketClient(new BotConfig("ws://127.0.0.1:8080"),blockingQueue);
        service.create();//创建websocket客户端
        Bot bot = service.createBot();//创建机器人实例，以调用api
        bot.sendGroupMsg(123456, MsgUtils.builder().text("123").build(), true);//发送群消息
        GroupMemberInfoResp sender = bot.getGroupMemberInfo(123456, 123456, false).getData();//获取响应的群成员信息
        System.out.println(sender.toString());
        EventDispatchers dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
        GroupMessageListener groupMessageListener = new GroupMessageListener();//自定义监听模式
        groupMessageListener.addHandler("天气", new Handler<GroupMessageEvent>() {
            @Override
            public void handle(GroupMessageEvent groupMessage) {
                System.out.println(groupMessage);

            }
        });
        dispatchers.addListener(groupMessageListener);//注册监听
        dispatchers.addListener(new SimpleListener<PrivateMessageEvent>() {//私聊监听
            @Override
            public void onMessage(PrivateMessageEvent privateMessage) {
                System.out.println(privateMessage);
            }
        });//快速监听模式

        dispatchers.start(10);//线程组处理任务

    }
}
