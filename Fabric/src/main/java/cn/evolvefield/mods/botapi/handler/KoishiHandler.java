package cn.evolvefield.mods.botapi.handler;

import cn.evolvefield.mods.botapi.util.JsonsObject;

import java.util.concurrent.BlockingQueue;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/5 23:27
 * Description:
 */
public class KoishiHandler implements Runnable{


    private final BlockingQueue<String> receive;


    public KoishiHandler(BlockingQueue<String> receive){
        this.receive = receive;
    }


    @Override
    public void run() {
        var msg = receive.poll();
        JsonsObject json = new JsonsObject(msg);
        var type = json.optString("type");
        var data = json.optJSONSObject("data");
        if (type.equals("cmd")) {
            var permission = data.optInt("permission");
            if (permission >= 3){

            }
            else {

            }



        } else if (type.equals("msg")) {
            var content = data.optString("content");
            TickEventHandler.getMsgReceive().add(content);
        } else if (type.equals("img")) {
            var url = data.optString("url");
            TickEventHandler.getMsgReceive().add(String.format("[[CICode,url=%s,name=来自QQ的图片]]", url));
        }
    }
}
