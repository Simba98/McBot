package cn.evolvefield.mods.botapi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Project: BotConnect-dev
 * Author: cnlimiter
 * Date: 2023/4/7 23:16
 * Description:
 */
public class GsonUtil {
    private static final Gson GSON = (new GsonBuilder()).enableComplexMapKeySerialization().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();
    private static final Gson GSON_NULL = (new GsonBuilder()).enableComplexMapKeySerialization().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();

    public GsonUtil() {
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Gson getWriteNullGson() {
        return GSON_NULL;
    }

    public static String toJsonStringIgnoreNull(Object object) {
        return GSON.toJson(object);
    }

    public static String toJsonString(Object object) {
        return GSON_NULL.toJson(object);
    }

    public static <T> T strToJavaBean(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> List<T> strToList(String gsonString, Class<T> cls) {
        return (List)GSON.fromJson(gsonString, (new TypeToken<List<T>>() {
        }).getType());
    }

    public static <T> List<Map<String, T>> strToListMaps(String gsonString) {
        return (List)GSON.fromJson(gsonString, (new TypeToken<List<Map<String, String>>>() {
        }).getType());
    }

    public static <T> Map<String, T> strToMaps(String gsonString) {
        return (Map)GSON.fromJson(gsonString, (new TypeToken<Map<String, T>>() {
        }).getType());
    }
}
