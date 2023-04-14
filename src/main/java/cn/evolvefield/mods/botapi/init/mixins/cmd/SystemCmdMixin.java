package cn.evolvefield.mods.botapi.init.mixins.cmd;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
    private static void say_register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("say").requires((p_198627_0_) -> {
            return p_198627_0_.hasPermission(2);
        }).then(Commands.argument("message", MessageArgument.message()).executes((p_198626_0_) -> {
            ITextComponent itextcomponent = MessageArgument.getMessage(p_198626_0_, "message");
            TranslationTextComponent translationtextcomponent = new TranslationTextComponent("chat.type.announcement", p_198626_0_.getSource().getDisplayName(), itextcomponent);
            Entity entity = p_198626_0_.getSource().getEntity();
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
                                String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", itextcomponent.getContents()));
                } else {
                    for (long id : ConfigHandler.cached().getCommon().getGroupIdList())
                        BotApi.bot.sendGroupMsg(id,
                                String.format("[" + ConfigHandler.cached().getCmd().getMcSystemPrefix() + "] %s", itextcomponent.getContents()),
                                true);
                }
            }
            /////////////////////////
            if (entity != null) {
                p_198626_0_.getSource().getServer().getPlayerList().broadcastMessage(translationtextcomponent, ChatType.CHAT, entity.getUUID());
            } else {
                p_198626_0_.getSource().getServer().getPlayerList().broadcastMessage(translationtextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
            }

            return 1;
        })));
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/impl/SayCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void sayRedirect(CommandDispatcher<CommandSource> dispatcher) {
        say_register(dispatcher);
    }
}
