package cn.evolvefield.mods.botapi.api.cmd;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:37
 * Version: 1.0
 */
public class CustomCmdUtil {
    public static CustomCmd loadFromJson(ResourceLocation id, JsonObject json) {

        var alies = GsonHelper.getAsString(json, "alies");
        var content = GsonHelper.getAsString(json, "content");
        int role = GsonHelper.getAsInt(json, "role", 0);

        var cmd = new CustomCmd(id, alies, content, role);

        var enabled = GsonHelper.getAsBoolean(json, "enabled", true);

        cmd.setEnabled(enabled);

        return cmd;
    }
}
