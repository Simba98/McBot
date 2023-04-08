package cn.evolvefield.mods.botapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/8 1:41
 * Description:
 */
public class RegexUtil {
    public RegexUtil() {
    }

    public static Matcher regexMatcher(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches() ? matcher : null;
    }
}
