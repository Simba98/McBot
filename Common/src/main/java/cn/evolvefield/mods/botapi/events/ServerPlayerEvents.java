package cn.evolvefield.mods.botapi.events;

import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.util.locale.I18a;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 14:03
 * Description:
 */
public class ServerPlayerEvents {
    public static void playerLoggedIn(Player player) {
        if (Constants.config.getStatus().isS_JOIN_ENABLE() && Constants.config.getStatus().isSEND_ENABLED()) {
            if (Constants.config.getCommon().isGuildOn() && !Constants.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : Constants.config.getCommon().getChannelIdList())
                    CommonBotApi.bot.sendGuildMsg(Constants.config.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 加入了服务器");
            } else {
                for (long id : Constants.config.getCommon().getGroupIdList())
                    CommonBotApi.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 加入了服务器", true);
            }
        }
    }


    public static void playerLoggedOut(Player player) {
        if (Constants.config.getStatus().isS_LEAVE_ENABLE() && Constants.config.getStatus().isSEND_ENABLED()) {
            if (Constants.config.getCommon().isGuildOn() && !Constants.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : Constants.config.getCommon().getChannelIdList())
                    CommonBotApi.bot.sendGuildMsg(Constants.config.getCommon().getGuildId(), id, player.getDisplayName().getString() + " 离开了服务器");
            } else {
                for (long id : Constants.config.getCommon().getGroupIdList())
                    CommonBotApi.bot.sendGroupMsg(id, player.getDisplayName().getString() + " 离开了服务器", true);

            }
        }
    }

    public static void playerDeath(DamageSource source, Player player) {
        if (player != null && Constants.config.getStatus().isS_DEATH_ENABLE() && Constants.config.getStatus().isSEND_ENABLED()) {
            LivingEntity livingEntity2 = player.getKillCredit();
            String string = "botapi.death.attack." + source.msgId;
            String string2 = string + ".player";
            String msg = livingEntity2 != null ? I18a.get(string2, player.getDisplayName().getString(), livingEntity2.getDisplayName().getString()) : I18a.get(string, player.getDisplayName().getString());

            if (Constants.config.getCommon().isGuildOn() && !Constants.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : Constants.config.getCommon().getChannelIdList())
                    CommonBotApi.bot.sendGuildMsg(Constants.config.getCommon().getGuildId(), id, String.format(msg, player.getDisplayName().getString()));
            } else {
                for (long id : Constants.config.getCommon().getGroupIdList())
                    CommonBotApi.bot.sendGroupMsg(id, String.format(msg, player.getDisplayName().getString()), true);
            }
        }
    }

    public static void playerAdvancement(Player player, Advancement advancement) {
        if (Constants.config.getStatus().isS_ADVANCE_ENABLE() && advancement.getDisplay() != null && Constants.config.getStatus().isSEND_ENABLED()) {
            String msg = I18a.get("botapi.chat.type.advancement." + advancement.getDisplay().getFrame().getName(), player.getDisplayName().getString(), I18a.get(advancement.getDisplay().getTitle().getString()));

            if (Constants.config.getCommon().isGuildOn() && !Constants.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : Constants.config.getCommon().getChannelIdList())
                    CommonBotApi.bot.sendGuildMsg(Constants.config.getCommon().getGuildId(), id, msg);
            } else {
                for (long id : Constants.config.getCommon().getGroupIdList())
                    CommonBotApi.bot.sendGroupMsg(id, msg, true);
            }
        }
    }


}
