package cn.evolvefield.mods.botapi.handler;

import cn.evolvefield.mods.botapi.core.ActionData;
import cn.evolvefield.mods.botapi.core.Frame;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.java_websocket.WebSocket;

import java.util.concurrent.Executors;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/6 20:18
 * Description:
 */
public class EventHandler {

    private final MinecraftServer server;
    private final WebSocket socket;
    private final Frame frame;

    public EventHandler(MinecraftServer server, WebSocket socket, Frame frame){
        this.server = server;
        this.socket = socket;
        this.frame = frame;
        run();
    }



    public void run(){
        final ListenableFuture<ActionData<?>> run = excute();
        callBack(run);
    }


    public ListenableFuture<ActionData<?>> excute(){
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4))
                .submit(() -> {
                    var data = new ActionData<>();
                    switch (frame.getType()){
                        case MSG -> {
                            var msg = frame.getData().optString("content", "");
                            Component textComponents = Component.literal(msg);
                            server.getPlayerList().broadcastSystemMessage(textComponents, false);
                        }
                        case CMD -> {

                        }
                        case IMG -> {
                            var msg = frame.getData().optString("url", "");
                            Component textComponents = Component.literal(String.format("[[CICode,url=%s,name=来自QQ的图片]]", msg));
                            server.getPlayerList().broadcastSystemMessage(textComponents, false);
                        }
                    }


                    Thread.sleep(2000L);
                    return data;
                });

    }

    public void callBack(ListenableFuture<ActionData<?>> run){
        Futures.addCallback(run, new FutureCallback<>() {
            @Override
            public void onSuccess(ActionData<?> result) {

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed, t: " + t);
            }
        }, Executors.newSingleThreadExecutor());


    }


}
