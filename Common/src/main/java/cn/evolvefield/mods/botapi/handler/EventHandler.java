package cn.evolvefield.mods.botapi.handler;

import cn.evolvefield.mods.botapi.core.action.Request;
import cn.evolvefield.mods.botapi.core.action.Response;
import cn.evolvefield.mods.botapi.core.dto.ActionSuccess;
import cn.evolvefield.mods.botapi.util.CQUtil;
import cn.evolvefield.mods.botapi.util.JsonsObject;
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
    private final Request request;


    public EventHandler(MinecraftServer server, WebSocket socket, Request request){
        this.server = server;
        this.socket = socket;
        this.request = request;

    }



    public void run(){
        callBack(excute());
    }


    private ListenableFuture<Response<?>> excute(){
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4))
                .submit(() -> {
                    var data = new Response<>();
                    var requestData = new JsonsObject(request.getParams().getData());
                    switch (request.getAction()){
                        case "send_msg" -> {
                            String from_id = "";
                            var nickname = requestData.optString("nickname");
                            var msg = requestData.optString("content", "");
                            var image_on = requestData.optBool("image_on");
                            var platform = request.getParams().getPlatform();
                            switch (platform){
                                case "qq" -> {
                                    if (request.getParams().getDetailType().equals("private")){
                                        from_id = requestData.optString("user_id");
                                    }
                                    else {
                                        from_id = requestData.optString("group_id");
                                    }
                                }
                                case "guild" , "kook" -> {
                                    if (request.getParams().getDetailType().equals("private")){
                                        from_id = requestData.optString("user_id");
                                    }
                                    else {
                                        from_id = requestData.optString("channel_id");
                                    }
                                }

                            }

                            msg = CQUtil.replace(msg, image_on);

                            String toSend = String.format("§b[§l%s§r(§5%s§b)]§a<%s>§f %s", platform, from_id, nickname, msg);
                            Component textComponents = Component.literal(toSend);
                            server.getPlayerList().broadcastSystemMessage(textComponents, false);
                            data = new ActionSuccess();
                        }
                        case "send_cmd" -> {
                            var nickname = requestData.optString("nickname");
                            var msg = requestData.optString("content", "");


                        }

                    }


                    //Thread.sleep(2000L);
                    return data;
                });

    }

    private void callBack(ListenableFuture<Response<?>> run){
        Futures.addCallback(run, new FutureCallback<>() {
            @Override
            public void onSuccess(Response<?> result) {
                if (result instanceof ActionSuccess){
                    return;
                }
                else {

                }

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed, t: " + t);
            }
        }, Executors.newSingleThreadExecutor());


    }


}
