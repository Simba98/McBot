package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.McBot;
import cn.evole.mods.mcbot.init.handler.ConfigHandler;
import cn.evole.mods.multi.common.ComponentWrapper;
import cn.evole.onebot.client.connection.ConnectFactory;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;

import java.util.regex.Pattern;

public class ConnectCommand {

    public static int cqhttpExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);


        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(ComponentWrapper.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
            ConfigHandler.cached().getBotConfig().setMiraiHttp(false);

            try {
                McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
                McBot.app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);
            ConfigHandler.save();
            return 1;

        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal(ChatFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);

        val pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ConfigHandler.cached().getBotConfig().setUrl("ws://" + parameter);
            context.getSource().sendSuccess(ComponentWrapper.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
            ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
            try {
                McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
                McBot.app.start();
            } catch (Exception e) {
                Const.LOGGER.error("§c机器人服务端配置不正确");
            }
            ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
            ConfigHandler.cached().getCommon().setEnable(true);
            ConfigHandler.save();
            return 1;

        } else {
            context.getSource().sendSuccess(ComponentWrapper.literal(ChatFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(ComponentWrapper.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "cqhttp"), true);
        ConfigHandler.cached().getBotConfig().setMiraiHttp(false);
        try {
            McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
            McBot.app.start();
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);
        ConfigHandler.save();
        return 1;

    }

    public static int miraiCommonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(ComponentWrapper.literal("尝试链接框架" + ChatFormatting.LIGHT_PURPLE + "mirai"), true);
        ConfigHandler.cached().getBotConfig().setMiraiHttp(true);
        try {
            McBot.app = new Thread(() -> {
                    McBot.service = new ConnectFactory(ConfigHandler.cached().getBotConfig(), McBot.blockingQueue);//创建websocket连接
                    McBot.bot = McBot.service.ws.createBot();//创建机器人实例
                }, "BotServer");
            McBot.app.start();
        } catch (Exception e) {
            Const.LOGGER.error("§c机器人服务端配置不正确");
        }
        ConfigHandler.cached().getStatus().setRECEIVE_ENABLED(true);
        ConfigHandler.cached().getCommon().setEnable(true);

        ConfigHandler.save();
        return 1;

    }

}
