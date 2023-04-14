package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.Const;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import cn.evolvefield.onebot.client.connection.ConnectFactory;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

import java.util.regex.Pattern;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);


        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            ConfigHandler.cached().getBotConfig().setMiraiHttp(false);

            try {
                BotApi.app.submit(() -> {
                    BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                    BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
                });
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);

        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
            try {
                BotApi.app.submit(() -> {
                    BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                    BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
                });
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(new TextComponent(ChatFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        ConfigHandler.cached().getBotConfig().setMiraiHttp(false);
        try {
            BotApi.app.submit(() -> {
                BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
            });
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(new TextComponent("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
        try {
            BotApi.app.submit(() -> {
                BotApi.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), BotApi.blockingQueue);//创建websocket连接
                BotApi.bot = BotApi.service.ws.createBot();//创建机器人实例
            });
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);

        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;

    }

}
