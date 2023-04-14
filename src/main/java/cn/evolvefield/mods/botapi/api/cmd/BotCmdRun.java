package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class BotCmdRun extends CommandSource {
    public static BotCmdRun CUSTOM = new BotCmdRun(ICommandSource.NULL, Vector3d.ZERO, Vector2f.ZERO, BotApi.SERVER.overworld(), 4,
            "Bot", new StringTextComponent("Bot"), BotApi.SERVER, null);

    public static BotCmdRun OP = new BotCmdRun(ICommandSource.NULL, Vector3d.ZERO, Vector2f.ZERO, BotApi.SERVER.overworld(), 4,
            "OP", new StringTextComponent("OP"), BotApi.SERVER, null);
    public List<String> outPut = new ArrayList<>();

    public BotCmdRun(ICommandSource pSource, Vector3d pWorldPosition, Vector2f pRotation, ServerWorld pLevel, int pPermissionLevel, String pTextName, ITextComponent pDisplayName, MinecraftServer pServer, @Nullable Entity pEntity) {
        super(pSource, pWorldPosition, pRotation, pLevel ,pPermissionLevel, pTextName, pDisplayName, pServer, pEntity);
    }

    @Override
    public void sendSuccess(ITextComponent component, boolean p_197030_2_) {
        super.sendSuccess(component, p_197030_2_);
        this.outPut.add(component.getString());
    }



}
