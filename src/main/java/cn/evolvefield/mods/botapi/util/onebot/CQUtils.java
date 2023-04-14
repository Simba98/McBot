package cn.evolvefield.mods.botapi.util.onebot;

import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.util.BotUtils;
import cn.evolvefield.onebot.sdk.util.RegexUtils;
import lombok.val;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/2/10 1:11
 * Description:
 */
public class CQUtils {

    private final static String CQ_CODE_SPLIT = "(?<=\\[CQ:[^]]{1,99999}])|(?=\\[CQ:[^]]{1,99999}])";

    private final static String CQ_CODE_REGEX = "\\[CQ:([^,\\[\\]]+)((?:,[^,=\\[\\]]+=[^,\\[\\]]*)*)]";


    public static boolean hasImg(String msg) {
        String regex = "\\[CQ:image,[(\\s\\S)]*\\]";
        val p = Pattern.compile(regex);
        val m = p.matcher(msg);
        return m.find();
    }

    public static String replace(String msg) {
        if (msg.indexOf('[') == -1)
            return BotUtils.unescape(msg);
        String message = "";
        val matcher = RegexUtils.regexMatcher(CQ_CODE_REGEX, msg);
        while (matcher.find()) {
            val type = matcher.group(1);
            switch (type) {
                case "image": {
                    if (ConfigHandler.cached().getCommon().isImageOn()) {
                        val url = Arrays.stream(matcher.group(2).split(","))//具体数据分割
                                .filter(it -> it.startsWith("url"))//非空判断
                                .map(it -> it.substring(it.indexOf('=')) + 1)
                                .findFirst();
                        if (url.isPresent()) {
                            message = String.format("[[CICode,url=%s,name=来自QQ的图片]]", url.get());
                        } else {
                            message = "[图片]";
                        }
                    } else
                        message = "[图片]";
                    break;
                }
                case "reply":
                    message = "[回复]";
                    break;
                case "at":
                    message = "[@]";
                    break;
                case "record":
                    message = "[语音]";
                    break;
                case "forward":
                    message = "[合并转发]";
                    break;
                case "video":
                    message = "[视频]";
                    break;
                case "music":
                    message = "[音乐]";
                    break;
                case "redbag":
                    message = "[红包]";
                    break;
                case "poke":
                    message = "[戳一戳]";
                    break;
                case "face":
                    message = "[表情]";
                    break;
                default:
                    message = "[?]";
                    break;
            }
        }

        return BotUtils.unescape(message);
    }
}
