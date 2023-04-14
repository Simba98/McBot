package cn.evolvefield.mods.botapi.init.mixins;

import cn.evolvefield.mods.botapi.init.callbacks.ServerLevelEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:30
 * Version: 1.0
 */
@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleChat(Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", shift = At.Shift.BEFORE))
    public void handleChat(String string, CallbackInfo ci) {
        Component component2 = new TextComponent(String.format("<%s> %s", this.player.getDisplayName(), string));
        ServerLevelEvents.Server_Chat.invoker().onChat(this.player, string, component2);
    }
}
