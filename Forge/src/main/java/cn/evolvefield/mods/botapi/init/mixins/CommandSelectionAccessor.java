package cn.evolvefield.mods.botapi.init.mixins;

import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Project: BotConnect-MultiLoader
 * Author: cnlimiter
 * Date: 2022/12/31 16:04
 * Description:
 */
@Mixin(Commands.CommandSelection.class)
public interface CommandSelectionAccessor {

    @Accessor
    boolean isIncludeDedicated();

}
