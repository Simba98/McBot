package cn.evole.mods.mcbot.util.onebot;

import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.config.ModConfig;
import cn.evole.onebot.sdk.entity.ArrayMsg;
import cn.evole.onebot.sdk.event.message.MessageEvent;
import cn.evole.onebot.sdk.util.BotUtils;
import cn.evole.onebot.sdk.util.json.GsonUtils;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.evole.onebot.sdk.util.BotUtils.arrayMsgToCode;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/2/10 1:11
 * Description:
 */
public class CQUtils {

    private final static String CQ_CODE_SPLIT = "(?<=\\[CQ:[^]]{1,99999}])|(?=\\[CQ:[^]]{1,99999}])";

    private final static String CQ_CODE_REGEX = "\\[CQ:(.*?),(.*?)]";


    public static boolean hasImg(String msg) {
        String regex = "\\[CQ:image,[(\\s\\S)]*]";
        val p = Pattern.compile(regex);
        val m = p.matcher(msg);
        return m.find();
    }

    public static void rawConvert(String message, MessageEvent event) {
        if (message.startsWith("[") && !message.startsWith("[CQ:")) {//如果是消息链（消息链以[开头）
            List<ArrayMsg> msg = GsonUtils.fromJson(message, new TypeToken<List<ArrayMsg>>() {
            }.getType());
            event.setArrayMsg(msg);
            event.setMessage(arrayMsgToCode(msg));
        }
    }

    public static String replace(MessageEvent event) {
        //将消息链转化成cq码
        rawConvert(event.getMessage(), event);
        //获取转化完的
        String msg = event.getMessage();

        String back;
        StringBuffer message = new StringBuffer();
        Pattern pattern = Pattern.compile(CQ_CODE_REGEX);
        Matcher matcher = pattern.matcher(msg);

        val call = new FutureTask<>(() -> {
            while (matcher.find()) {//全局匹配
                val type = matcher.group(1);
                val data = matcher.group(2);
                switch (type) {
                    case "image":
                        if (ModConfig.INSTANCE.getCommon().isImageOn() && Const.isLoad("chatimage")) {
                            val url = Arrays.stream(data.split(","))//具体数据分割
                                    .filter(it -> it.startsWith("url"))//非空判断
                                    .map(it -> it.substring(it.indexOf('=') + 1))
                                    .findFirst();
                            if (url.isPresent()) {
                                matcher.appendReplacement(message, String.format("[[CICode,url=%s,name=来自QQ的图片]]", url.get()));
                            } else {
                                matcher.appendReplacement(message, "[图片]");
                            }
                        } else {
                            matcher.appendReplacement(message, "[图片]");
                        }
                        break;
                    case "at":
                        val id = Arrays.stream(data.split(","))//具体数据分割
                                .filter(it -> it.startsWith("qq"))//非空判断
                                .map(it -> it.substring(it.indexOf('=') + 1))
                                .findFirst();
                        if (id.isPresent()) {
                            matcher.appendReplacement(message, String.format("[@%s]", BotUtils.getNickname(Long.parseLong(id.get()))));
                        } else {
                            matcher.appendReplacement(message, "[@]");
                        }
                        break;
                    case "record":
                        matcher.appendReplacement(message, "[语音]");
                        break;
                    case "forward":
                        matcher.appendReplacement(message, "[合并转发]");
                        break;
                    case "video":
                        matcher.appendReplacement(message, "[视频]");
                        break;
                    case "music":
                        matcher.appendReplacement(message, "[音乐]");
                        break;
                    case "redbag":
                        matcher.appendReplacement(message, "[红包]");
                        break;
                    case "face":
                        matcher.appendReplacement(message, "[表情]");
                        break;
                    case "reply":
                        matcher.appendReplacement(message, "[回复]");
                        break;
                    default:
                        matcher.appendReplacement(message, "[?]");
                        break;
                }
            }
            matcher.appendTail(message);
            return message.toString();
        });
        try {
            McBot.CQUtilsExecutor.execute(call);
            back = call.get(1000 * 3, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException | IllegalStateException e) {
            back = msg;
            call.cancel(true);
            Const.LOGGER.error(e.getLocalizedMessage());
        }
        return back;
    }
}
