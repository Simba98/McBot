package cn.evolvefield.mods.botapi.init.handler;


import cn.evolvefield.mods.botapi.events.ServerPlayerEvents;
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
        ServerPlayerEvents.playerLoggedIn(event.getEntity());
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayerEvents.playerLoggedOut(event.getEntity());

    }


    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ServerPlayerEvents.playerDeath(event.getSource(), player);
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent.AdvancementEarnEvent event) {
        ServerPlayerEvents.playerAdvancement(event.getEntity(), event.getAdvancement());
    }

}
