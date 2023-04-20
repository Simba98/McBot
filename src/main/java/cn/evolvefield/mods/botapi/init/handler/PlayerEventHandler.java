package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.util.locale.I18n;
import net.minecraft.world.entity.LivingEntity;
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
        var player = event.getEntity();
        if (ConfigHandler.cached().getStatus().isS_JOIN_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 加入了服务器", true);
            }
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        var player = event.getEntity();
        if (ConfigHandler.cached().getStatus().isS_LEAVE_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 离开了服务器", true);

            }
        }
    }


    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        var player = event.getEntity();
        var source = event.getSource();
        if (player instanceof Player && ConfigHandler.cached().getStatus().isS_DEATH_ENABLE() && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            LivingEntity livingEntity2 = player.getKillCredit();
            String string = "botapi.death.attack." + source.msgId;
            String msg = livingEntity2 != null ? I18n.get(string, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18n.get(string, player.getDisplayName().getString());

            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName().getString()), true);
            }
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent.AdvancementEarnEvent event) {
        var player = event.getEntity();
        var advancement = event.getAdvancement();
        if (ConfigHandler.cached().getStatus().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && ConfigHandler.cached().getStatus().isSEND_ENABLED()) {
            String msg = I18n.get("botapi.chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName().getString(), I18n.get(advancement.getDisplay().getTitle().getString()));

            if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                    BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(), id, msg);
            } else {
                for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                    BotApi.bot.sendGroupMsg(id, msg, true);
            }
        }
    }
}
