package cn.evolvefield.mods.botapi.events;

import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import net.minecraft.server.level.ServerPlayer;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 14:08
 * Description:
 */
public class ChatEvents {
    public static void init(ServerPlayer player, String message) {
        var split = message.split(" ");
        if (Constants.config != null
                && Constants.config.getStatus().isS_CHAT_ENABLE()
                && Constants.config.getStatus().isSEND_ENABLED()) {
            if (Constants.config.getCommon().isGuildOn() && !Constants.config.getCommon().getChannelIdList().isEmpty()) {
                for (String id : Constants.config.getCommon().getChannelIdList())
                    CommonBotApi.bot.sendGuildMsg(Constants.config.getCommon().getGuildId(),
                            id,
                            String.format("[" + Constants.config.getCmd().getMcPrefix() + "]<%s> %s",
                                    player.getDisplayName().getString(),
                                    Constants.config.getCmd().isMcChatPrefixEnable()
                                            && Constants.config.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message));
            } else {
                for (long id : Constants.config.getCommon().getGroupIdList())
                    CommonBotApi.bot.sendGroupMsg(id,
                            String.format("[" + Constants.config.getCmd().getMcPrefix() + "]<%s> %s",
                                    player.getDisplayName().getString(),
                                    Constants.config.getCmd().isMcChatPrefixEnable()
                                            && Constants.config.getCmd().getMcChatPrefix().equals(split[0]) ? split[1] : message),
                            true);
            }


        }
    }
}
