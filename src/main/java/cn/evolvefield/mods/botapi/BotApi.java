package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.sdk.config.BotConfig;
import cn.evolvefield.mods.botapi.sdk.connection.ConnectFactory;
import cn.evolvefield.mods.botapi.sdk.connection.ModWebSocketClient;
import cn.evolvefield.mods.botapi.sdk.core.Bot;
import cn.evolvefield.mods.botapi.sdk.listener.Handler;
import cn.evolvefield.mods.botapi.sdk.listener.SimpleListener;
import cn.evolvefield.mods.botapi.sdk.listener.impl.GroupMessageListener;
import cn.evolvefield.mods.botapi.sdk.model.event.EventDispatchers;
import cn.evolvefield.mods.botapi.sdk.model.event.message.GroupMessageEvent;
import cn.evolvefield.mods.botapi.sdk.model.event.message.PrivateMessageEvent;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BotApi.MODID)
public class BotApi {

    public static final String MODID = "botapi";
    private static final Logger LOGGER = LogUtils.getLogger();
    LinkedBlockingQueue<String> blockingQueue;
    public static ModWebSocketClient service;
    public static EventDispatchers dispatchers;

    public static Bot bot;
    public BotApi() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws Exception{
        this.blockingQueue = new LinkedBlockingQueue<>();//使用队列传输数据
        service = ConnectFactory.createWebsocketClient(new BotConfig("ws://127.0.0.1:8080", null, false), blockingQueue);
        service.create();
        bot = service.createBot();
        dispatchers = new EventDispatchers(blockingQueue);//创建事件分发器
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

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event){
        this.dispatchers.stop();
    }

}
