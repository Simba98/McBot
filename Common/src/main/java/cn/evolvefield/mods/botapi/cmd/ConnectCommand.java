package cn.evolvefield.mods.botapi.cmd;


import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import cn.evolvefield.mods.botapi.handler.ConfigHandler;
import cn.evolvefield.onebot.sdk.connection.ConnectFactory;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.regex.Pattern;

;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);


        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            Constants.config.getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            Constants.config.getBotConfig().setMiraiHttp(false);
            try {
                CommonBotApi.service = ConnectFactory.createWebsocketClient(Constants.config.getBotConfig(), CommonBotApi.blockingQueue);
                CommonBotApi.service.create();//创建websocket连接
                CommonBotApi.bot = CommonBotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Constants.LOGGER.error(e.getMessage());
            }
            Constants.config.getStatus().setRECEIVE_ENABLED(true);
            Constants.config.getCommon().setEnable(true);
            ConfigHandler.save(Constants.config);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var parameter = context.getArgument("parameter", String.class);

        var pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        var matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            Constants.config.getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            Constants.config.getBotConfig().setMiraiHttp(true);
            try {
                CommonBotApi.service = ConnectFactory.createWebsocketClient(Constants.config.getBotConfig(), CommonBotApi.blockingQueue);
                CommonBotApi.service.create();//创建websocket连接
                CommonBotApi.bot = CommonBotApi.service.createBot();//创建机器人实例
            } catch (Exception e) {
                Constants.LOGGER.error(e.getMessage());
            }
            Constants.config.getStatus().setRECEIVE_ENABLED(true);
            Constants.config.getCommon().setEnable(true);
            ConfigHandler.save(Constants.config);

            return Command.SINGLE_SUCCESS;

        } else {
            context.getSource().sendSuccess(Component.literal(ChatFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        Constants.config.getBotConfig().setMiraiHttp(false);
        try {
            CommonBotApi.service = ConnectFactory.createWebsocketClient(Constants.config.getBotConfig(), CommonBotApi.blockingQueue);
            CommonBotApi.service.create();//创建websocket连接
            CommonBotApi.bot = CommonBotApi.service.createBot();//创建机器人实例
        } catch (Exception e) {
            Constants.LOGGER.error(e.getMessage());
        }
        Constants.config.getStatus().setRECEIVE_ENABLED(true);
        Constants.config.getCommon().setEnable(true);
        ConfigHandler.save(Constants.config);
        return Command.SINGLE_SUCCESS;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(Component.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        Constants.config.getBotConfig().setMiraiHttp(true);
        try {
            CommonBotApi.service = ConnectFactory.createWebsocketClient(Constants.config.getBotConfig(), CommonBotApi.blockingQueue);
            CommonBotApi.service.create();//创建websocket连接
            CommonBotApi.bot = CommonBotApi.service.createBot();//创建机器人实例
        } catch (Exception e) {
            Constants.LOGGER.error(e.getMessage());
        }
        Constants.config.getStatus().setRECEIVE_ENABLED(true);
        Constants.config.getCommon().setEnable(true);
        ConfigHandler.save(Constants.config);

        return Command.SINGLE_SUCCESS;

    }

}
