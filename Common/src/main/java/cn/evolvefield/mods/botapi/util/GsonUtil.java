package cn.evolvefield.mods.botapi.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Project: BotConnect-MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 16:22
 * Description:
 */
public class GsonUtil {
    private static final Gson GSON = (new GsonBuilder()).create();

    public GsonUtil() {
    }

    public static boolean isStringValue(JsonObject $$0, String $$1) {
        return isValidPrimitive($$0, $$1) && $$0.getAsJsonPrimitive($$1).isString();
    }

    public static boolean isStringValue(JsonElement $$0) {
        return $$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isString();
    }

    public static boolean isNumberValue(JsonObject $$0, String $$1) {
        return isValidPrimitive($$0, $$1) && $$0.getAsJsonPrimitive($$1).isNumber();
    }

    public static boolean isNumberValue(JsonElement $$0) {
        return $$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber();
    }

    public static boolean isBooleanValue(JsonObject $$0, String $$1) {
        return isValidPrimitive($$0, $$1) && $$0.getAsJsonPrimitive($$1).isBoolean();
    }

    public static boolean isBooleanValue(JsonElement $$0) {
        return $$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isBoolean();
    }

    public static boolean isArrayNode(JsonObject $$0, String $$1) {
        return isValidNode($$0, $$1) && $$0.get($$1).isJsonArray();
    }

    public static boolean isObjectNode(JsonObject $$0, String $$1) {
        return isValidNode($$0, $$1) && $$0.get($$1).isJsonObject();
    }

    public static boolean isValidPrimitive(JsonObject $$0, String $$1) {
        return !isValidNode($$0, $$1) ? false : $$0.get($$1).isJsonPrimitive();
    }

    public static boolean isValidNode(JsonObject $$0, String $$1) {
        if ($$0 == null) {
            return false;
        } else {
            return $$0.get($$1) != null;
        }
    }

    public static String convertToString(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive()) {
            return $$0.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a string, was " + getType($$0));
        }
    }

    public static String getAsString(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToString($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a string");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static String getAsString(JsonObject $$0, String $$1, @Nullable String $$2) {
        return $$0.has($$1) ? convertToString($$0.get($$1), $$1) : $$2;
    }

    public static Item convertToItem(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive()) {
            String $$2 = $$0.getAsString();
            return (Item) BuiltInRegistries.ITEM.getOptional(new ResourceLocation($$2)).orElseThrow(() -> {
                return new JsonSyntaxException("Expected " + $$1 + " to be an item, was unknown string '" + $$2 + "'");
            });
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be an item, was " + getType($$0));
        }
    }

    public static Item getAsItem(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToItem($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find an item");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static Item getAsItem(JsonObject $$0, String $$1, @Nullable Item $$2) {
        return $$0.has($$1) ? convertToItem($$0.get($$1), $$1) : $$2;
    }

    public static boolean convertToBoolean(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive()) {
            return $$0.getAsBoolean();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Boolean, was " + getType($$0));
        }
    }

    public static boolean getAsBoolean(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToBoolean($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Boolean");
        }
    }

    public static boolean getAsBoolean(JsonObject $$0, String $$1, boolean $$2) {
        return $$0.has($$1) ? convertToBoolean($$0.get($$1), $$1) : $$2;
    }

    public static double convertToDouble(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsDouble();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Double, was " + getType($$0));
        }
    }

    public static double getAsDouble(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToDouble($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Double");
        }
    }

    public static double getAsDouble(JsonObject $$0, String $$1, double $$2) {
        return $$0.has($$1) ? convertToDouble($$0.get($$1), $$1) : $$2;
    }

    public static float convertToFloat(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsFloat();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Float, was " + getType($$0));
        }
    }

    public static float getAsFloat(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToFloat($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Float");
        }
    }

    public static float getAsFloat(JsonObject $$0, String $$1, float $$2) {
        return $$0.has($$1) ? convertToFloat($$0.get($$1), $$1) : $$2;
    }

    public static long convertToLong(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsLong();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Long, was " + getType($$0));
        }
    }

    public static long getAsLong(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToLong($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Long");
        }
    }

    public static long getAsLong(JsonObject $$0, String $$1, long $$2) {
        return $$0.has($$1) ? convertToLong($$0.get($$1), $$1) : $$2;
    }

    public static int convertToInt(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Int, was " + getType($$0));
        }
    }

    public static int getAsInt(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToInt($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Int");
        }
    }

    public static int getAsInt(JsonObject $$0, String $$1, int $$2) {
        return $$0.has($$1) ? convertToInt($$0.get($$1), $$1) : $$2;
    }

    public static byte convertToByte(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsByte();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Byte, was " + getType($$0));
        }
    }

    public static byte getAsByte(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToByte($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Byte");
        }
    }

    public static byte getAsByte(JsonObject $$0, String $$1, byte $$2) {
        return $$0.has($$1) ? convertToByte($$0.get($$1), $$1) : $$2;
    }

    public static char convertToCharacter(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsCharacter();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Character, was " + getType($$0));
        }
    }

    public static char getAsCharacter(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToCharacter($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Character");
        }
    }

    public static char getAsCharacter(JsonObject $$0, String $$1, char $$2) {
        return $$0.has($$1) ? convertToCharacter($$0.get($$1), $$1) : $$2;
    }

    public static BigDecimal convertToBigDecimal(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsBigDecimal();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a BigDecimal, was " + getType($$0));
        }
    }

    public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToBigDecimal($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigDecimal");
        }
    }

    public static BigDecimal getAsBigDecimal(JsonObject $$0, String $$1, BigDecimal $$2) {
        return $$0.has($$1) ? convertToBigDecimal($$0.get($$1), $$1) : $$2;
    }

    public static BigInteger convertToBigInteger(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsBigInteger();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a BigInteger, was " + getType($$0));
        }
    }

    public static BigInteger getAsBigInteger(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToBigInteger($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a BigInteger");
        }
    }

    public static BigInteger getAsBigInteger(JsonObject $$0, String $$1, BigInteger $$2) {
        return $$0.has($$1) ? convertToBigInteger($$0.get($$1), $$1) : $$2;
    }

    public static short convertToShort(JsonElement $$0, String $$1) {
        if ($$0.isJsonPrimitive() && $$0.getAsJsonPrimitive().isNumber()) {
            return $$0.getAsShort();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a Short, was " + getType($$0));
        }
    }

    public static short getAsShort(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToShort($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a Short");
        }
    }

    public static short getAsShort(JsonObject $$0, String $$1, short $$2) {
        return $$0.has($$1) ? convertToShort($$0.get($$1), $$1) : $$2;
    }

    public static JsonObject convertToJsonObject(JsonElement $$0, String $$1) {
        if ($$0.isJsonObject()) {
            return $$0.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonObject, was " + getType($$0));
        }
    }

    public static JsonObject getAsJsonObject(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToJsonObject($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonObject");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonObject getAsJsonObject(JsonObject $$0, String $$1, @Nullable JsonObject $$2) {
        return $$0.has($$1) ? convertToJsonObject($$0.get($$1), $$1) : $$2;
    }

    public static JsonArray convertToJsonArray(JsonElement $$0, String $$1) {
        if ($$0.isJsonArray()) {
            return $$0.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + $$1 + " to be a JsonArray, was " + getType($$0));
        }
    }

    public static JsonArray getAsJsonArray(JsonObject $$0, String $$1) {
        if ($$0.has($$1)) {
            return convertToJsonArray($$0.get($$1), $$1);
        } else {
            throw new JsonSyntaxException("Missing " + $$1 + ", expected to find a JsonArray");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonArray getAsJsonArray(JsonObject $$0, String $$1, @Nullable JsonArray $$2) {
        return $$0.has($$1) ? convertToJsonArray($$0.get($$1), $$1) : $$2;
    }

    public static <T> T convertToObject(@Nullable JsonElement $$0, String $$1, JsonDeserializationContext $$2, Class<? extends T> $$3) {
        if ($$0 != null) {
            return $$2.deserialize($$0, $$3);
        } else {
            throw new JsonSyntaxException("Missing " + $$1);
        }
    }

    public static <T> T getAsObject(JsonObject $$0, String $$1, JsonDeserializationContext $$2, Class<? extends T> $$3) {
        if ($$0.has($$1)) {
            return convertToObject($$0.get($$1), $$1, $$2, $$3);
        } else {
            throw new JsonSyntaxException("Missing " + $$1);
        }
    }

    @Nullable
    @Contract("_,_,!null,_,_->!null;_,_,null,_,_->_")
    public static <T> T getAsObject(JsonObject $$0, String $$1, @Nullable T $$2, JsonDeserializationContext $$3, Class<? extends T> $$4) {
        return $$0.has($$1) ? convertToObject($$0.get($$1), $$1, $$3, $$4) : $$2;
    }

    public static String getType(@Nullable JsonElement $$0) {
        String $$1 = StringUtils.abbreviateMiddle(String.valueOf($$0), "...", 10);
        if ($$0 == null) {
            return "null (missing)";
        } else if ($$0.isJsonNull()) {
            return "null (json)";
        } else if ($$0.isJsonArray()) {
            return "an array (" + $$1 + ")";
        } else if ($$0.isJsonObject()) {
            return "an object (" + $$1 + ")";
        } else {
            if ($$0.isJsonPrimitive()) {
                JsonPrimitive $$2 = $$0.getAsJsonPrimitive();
                if ($$2.isNumber()) {
                    return "a number (" + $$1 + ")";
                }

                if ($$2.isBoolean()) {
                    return "a boolean (" + $$1 + ")";
                }
            }

            return $$1;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson $$0, Reader $$1, Class<T> $$2, boolean $$3) {
        try {
            JsonReader $$4 = new JsonReader($$1);
            $$4.setLenient($$3);
            return $$0.getAdapter($$2).read($$4);
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }

    public static <T> T fromJson(Gson $$0, Reader $$1, Class<T> $$2, boolean $$3) {
        T $$4 = fromNullableJson($$0, $$1, $$2, $$3);
        if ($$4 == null) {
            throw new JsonParseException("JSON data was null or empty");
        } else {
            return $$4;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson $$0, Reader $$1, TypeToken<T> $$2, boolean $$3) {
        try {
            JsonReader $$4 = new JsonReader($$1);
            $$4.setLenient($$3);
            return $$0.getAdapter($$2).read($$4);
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }

    public static <T> T fromJson(Gson $$0, Reader $$1, TypeToken<T> $$2, boolean $$3) {
        T $$4 = fromNullableJson($$0, $$1, $$2, $$3);
        if ($$4 == null) {
            throw new JsonParseException("JSON data was null or empty");
        } else {
            return $$4;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson $$0, String $$1, TypeToken<T> $$2, boolean $$3) {
        return (T) fromNullableJson($$0, (Reader) (new StringReader($$1)), (TypeToken) $$2, $$3);
    }

    public static <T> T fromJson(Gson $$0, String $$1, Class<T> $$2, boolean $$3) {
        return (T) fromJson($$0, (Reader) (new StringReader($$1)), (Class) $$2, $$3);
    }

    @Nullable
    public static <T> T fromNullableJson(Gson $$0, String $$1, Class<T> $$2, boolean $$3) {
        return (T) fromNullableJson($$0, (Reader) (new StringReader($$1)), (Class) $$2, $$3);
    }

    public static <T> T fromJson(Gson $$0, Reader $$1, TypeToken<T> $$2) {
        return fromJson($$0, $$1, $$2, false);
    }

    @Nullable
    public static <T> T fromNullableJson(Gson $$0, String $$1, TypeToken<T> $$2) {
        return fromNullableJson($$0, $$1, $$2, false);
    }

    public static <T> T fromJson(Gson $$0, Reader $$1, Class<T> $$2) {
        return fromJson($$0, $$1, $$2, false);
    }

    public static <T> T fromJson(Gson $$0, String $$1, Class<T> $$2) {
        return fromJson($$0, $$1, $$2, false);
    }

    public static JsonObject parse(String $$0, boolean $$1) {
        return parse((Reader) (new StringReader($$0)), $$1);
    }

    public static JsonObject parse(Reader $$0, boolean $$1) {
        return (JsonObject) fromJson(GSON, $$0, JsonObject.class, $$1);
    }

    public static JsonObject parse(String $$0) {
        return parse($$0, false);
    }

    public static JsonObject parse(Reader $$0) {
        return parse($$0, false);
    }

    public static JsonArray parseArray(String $$0) {
        return parseArray((Reader) (new StringReader($$0)));
    }

    public static JsonArray parseArray(Reader $$0) {
        return (JsonArray) fromJson(GSON, $$0, JsonArray.class, false);
    }

    public static String toStableString(JsonElement $$0) {
        StringWriter $$1 = new StringWriter();
        JsonWriter $$2 = new JsonWriter($$1);

        try {
            writeValue($$2, $$0, Comparator.naturalOrder());
        } catch (IOException var4) {
            throw new AssertionError(var4);
        }

        return $$1.toString();
    }

    public static void writeValue(JsonWriter $$0, @Nullable JsonElement $$1, @Nullable Comparator<String> $$2) throws IOException {
        if ($$1 != null && !$$1.isJsonNull()) {
            if ($$1.isJsonPrimitive()) {
                JsonPrimitive $$3 = $$1.getAsJsonPrimitive();
                if ($$3.isNumber()) {
                    $$0.value($$3.getAsNumber());
                } else if ($$3.isBoolean()) {
                    $$0.value($$3.getAsBoolean());
                } else {
                    $$0.value($$3.getAsString());
                }
            } else {
                Iterator var5;
                if ($$1.isJsonArray()) {
                    $$0.beginArray();
                    var5 = $$1.getAsJsonArray().iterator();

                    while (var5.hasNext()) {
                        JsonElement $$4 = (JsonElement) var5.next();
                        writeValue($$0, $$4, $$2);
                    }

                    $$0.endArray();
                } else {
                    if (!$$1.isJsonObject()) {
                        throw new IllegalArgumentException("Couldn't write " + $$1.getClass());
                    }

                    $$0.beginObject();
                    var5 = sortByKeyIfNeeded($$1.getAsJsonObject().entrySet(), $$2).iterator();

                    while (var5.hasNext()) {
                        Map.Entry<String, JsonElement> $$5 = (Map.Entry) var5.next();
                        $$0.name((String) $$5.getKey());
                        writeValue($$0, (JsonElement) $$5.getValue(), $$2);
                    }

                    $$0.endObject();
                }
            }
        } else {
            $$0.nullValue();
        }

    }

    private static Collection<Map.Entry<String, JsonElement>> sortByKeyIfNeeded(Collection<Map.Entry<String, JsonElement>> $$0, @Nullable Comparator<String> $$1) {
        if ($$1 == null) {
            return $$0;
        } else {
            List<Map.Entry<String, JsonElement>> $$2 = new ArrayList($$0);
            $$2.sort(Map.Entry.comparingByKey($$1));
            return $$2;
        }
    }
}
