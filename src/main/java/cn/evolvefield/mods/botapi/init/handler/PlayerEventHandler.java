package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.util.locale.I18a;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getStatus().isS_JOIN_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, event.getPlayer().getDisplayName().getString() + " 加入了服务器");
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.getPlayer().getDisplayName().getString() + " 加入了服务器");
            }
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getStatus().isS_LEAVE_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, event.getPlayer().getDisplayName().getString() + " 离开了服务器");
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.getPlayer().getDisplayName().getString() + " 离开了服务器");

            }
        }
    }


    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player && BotApi.config.getStatus().isS_DEATH_ENABLE() && BotApi.config.getStatus().isSEND_ENABLED()) {
            String message = event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString();
            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, String.format(message, event.getEntity().getDisplayName().getString()));
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), String.format(message, event.getEntity().getDisplayName().getString()));
            }
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if (BotApi.config.getStatus().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null && BotApi.config.getStatus().isSEND_ENABLED()) {
            String msg = I18a.get("botapi.chat.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), event.getPlayer().getDisplayName().getString(), I18a.get(event.getAdvancement().getDisplay().getTitle().getString()));

            if (BotApi.config.getCommon().isGuildOn() && !BotApi.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : BotApi.config.getCommon().getChannelIdList())
                    SendMessage.ChannelGroup(BotApi.config.getCommon().getGuildId(), id, msg);
            } else {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
            }

        }
    }
}
