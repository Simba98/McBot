package cn.evolvefield.mods.botapi.handler;

import blue.endless.jankson.Jankson;
import cn.evolvefield.mods.botapi.core.ActionData;
import cn.evolvefield.mods.botapi.core.Frame;
import cn.evolvefield.mods.botapi.core.action.Request;
import cn.evolvefield.mods.botapi.core.dto.ActionSuccess;
import cn.evolvefield.mods.botapi.util.CQUtil;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.java_websocket.WebSocket;

import java.util.concurrent.Executors;

import static cn.evolvefield.mods.botapi.core.action.ActionPath.SEND_MSG;

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


    private ListenableFuture<ActionData<?>> excute(){
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4))
                .submit(() -> {
                    var data = new ActionData<>();
                    var requestData = request.getParams().getData();
                    switch (request.getAction()){
                        case SEND_MSG -> {
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
                                case "guild" -> {
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
                            Component textComponents = Component.literal(msg);
                            server.getPlayerList().broadcastSystemMessage(textComponents, false);
                            data = new ActionSuccess();
                        }
                        case SEND_CMD -> {
                            var cmd = frame.getData().optString("cmd", "");
                            var permission = frame.getData().optInt("permission", 1);

                        }
                        case IMG -> {
                            var msg = frame.getData().optString("url", "");
                            Component textComponents = Component.literal(String.format("[[CICode,url=%s,name=来自QQ的图片]]", msg));
                            server.getPlayerList().broadcastSystemMessage(textComponents, false);
                            data = new ActionSuccess();
                        }
                    }


                    //Thread.sleep(2000L);
                    return data;
                });

    }

    private void callBack(ListenableFuture<ActionData<?>> run){
        Futures.addCallback(run, new FutureCallback<>() {
            @Override
            public void onSuccess(ActionData<?> result) {
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
