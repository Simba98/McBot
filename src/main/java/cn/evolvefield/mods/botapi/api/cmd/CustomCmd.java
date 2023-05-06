package cn.evolvefield.mods.botapi.api.cmd;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:19
 * Version: 1.0
 */
public class CustomCmd {
    private final String cmdAlies;

    private final String cmdContent;

    private final int requirePermission;

    private boolean enabled = true;

    public CustomCmd(String cmdAlies, String cmdContent, int requirePermission) {
        this.cmdAlies = cmdAlies;
        this.cmdContent = cmdContent;
        this.requirePermission = requirePermission;
    }

    public static CustomCmd loadFromJson(JsonObject json) {

        var alies = GsonHelper.getAsString(json, "alies");
        var content = GsonHelper.getAsString(json, "content");
        int role = GsonHelper.getAsInt(json, "role", 0);

        var cmd = new CustomCmd(alies, content, role);

        var enabled = GsonHelper.getAsBoolean(json, "enabled", true);

        cmd.setEnabled(enabled);

        return cmd;
    }

    public String getCmdAlies() {
        return cmdAlies;
    }

    public String getCmdContent() {
        return cmdContent;
    }

    public int getRequirePermission() {
        return requirePermission;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
