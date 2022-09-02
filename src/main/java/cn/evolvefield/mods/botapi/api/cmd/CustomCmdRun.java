package cn.evolvefield.mods.botapi.api.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/2 13:51
 * Version: 1.0
 */
public class CustomCmdRun extends CommandSourceStack {
    public List<String> outPut;

    public CustomCmdRun(CommandSource p_81302_, Vec3 p_81303_, Vec2 p_81304_, ServerLevel p_81305_, int p_81306_, String p_81307_, Component p_81308_, MinecraftServer p_81309_, @Nullable Entity p_81310_) {
        super(p_81302_, p_81303_, p_81304_, p_81305_, p_81306_, p_81307_, p_81308_, p_81309_, p_81310_);
    }

    @Override
    public void sendSuccess(Component component, boolean p_81356_) {
        this.outPut.add(component.getString());
        super.sendSuccess(component, p_81356_);
    }


}
