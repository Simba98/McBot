package cn.evolvefield.mods.botapi.init.handler;

import cn.evolvefield.mods.botapi.events.ServerPlayerEvents;
import cn.evolvefield.mods.botapi.init.callbacks.PlayerEvents;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 9:48
 * Version: 1.0
 */
public class PlayerEventHandler {
    public static void init() {
        PlayerEvents.PLAYER_LOGGED_IN.register((world, player) -> {
            ServerPlayerEvents.playerLoggedIn(player);
        });

        PlayerEvents.PLAYER_LOGGED_OUT.register((world, player) -> {
            ServerPlayerEvents.playerLoggedOut(player);
        });

        PlayerEvents.PLAYER_DEATH.register(ServerPlayerEvents::playerDeath);

        PlayerEvents.PLAYER_ADVANCEMENT.register(ServerPlayerEvents::playerAdvancement);
    }
}
