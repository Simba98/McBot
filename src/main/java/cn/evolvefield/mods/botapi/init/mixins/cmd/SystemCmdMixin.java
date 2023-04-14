package cn.evolvefield.mods.botapi.init.mixins.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Project: Bot-Connect-fabric-1.18
 * Author: cnlimiter
 * Date: 2023/1/23 18:52
 * Description:
 */
@Mixin(value = Commands.class)
public class SystemCmdMixin {
    private static void say_register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("say").requires((p_138414_) -> {
            return p_138414_.hasPermission(2);
        }).then(Commands.argument("message", MessageArgument.message()).executes((p_138412_) -> {
            Component component = MessageArgument.getMessage(p_138412_, "message");
            Component component1 = new TextComponent(String.format("[%s] %s", p_138412_.getSource().getDisplayName(), component));
            Entity entity = p_138412_.getSource().getEntity();
            /////////////////////////
            if (ModList.get().isLoaded("botapi")
                    && ConfigHandler.cached() != null
                    && ConfigHandler.cached().getStatus().isS_CHAT_ENABLE()
                    && ConfigHandler.cached().getStatus().isSEND_ENABLED()
                    && ConfigHandler.cached().getCmd().isMcSystemPrefixEnable()) {
                if (ConfigHandler.cached().getCommon().isGuildOn() && !ConfigHandler.cached().getCommon().getChannelIdList().isEmpty()) {
                    for (String id : ConfigHandler.cached().getCommon().getChannelIdList())
                        BotApi.bot.sendGuildMsg(ConfigHandler.cached().getCommon().getGuildId(),
                                id,
                                String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", component.getContents()));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        BotApi.bot.sendGroupMsg(id,
                                String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", component.getContents()),
                                true);
                }
            }
            /////////////////////////
            if (entity != null) {
                p_138412_.getSource().getServer().getPlayerList().broadcastMessage(component1, ChatType.CHAT, entity.getUUID());
            } else {
                p_138412_.getSource().getServer().getPlayerList().broadcastMessage(component1, ChatType.SYSTEM, Util.NIL_UUID);
            }

            return 1;
        })));

    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/SayCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void sayRedirect(CommandDispatcher<CommandSourceStack> dispatcher) {
        say_register(dispatcher);
    }
}
