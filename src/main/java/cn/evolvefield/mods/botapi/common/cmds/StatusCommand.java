package cn.evolvefield.mods.botapi.common.cmds;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.init.handler.ConfigHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class StatusCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean clientEnabled = ConfigHandler.cached().getCommon().isEnable();

        boolean receiveEnabled = ConfigHandler.cached().getStatus().isRECEIVE_ENABLED();
        boolean rChatEnabled = ConfigHandler.cached().getStatus().isR_CHAT_ENABLE();
        boolean rCmdEnabled = ConfigHandler.cached().getStatus().isR_COMMAND_ENABLED();

        boolean sendEnabled = ConfigHandler.cached().getStatus().isSEND_ENABLED();
        boolean sJoinEnabled = ConfigHandler.cached().getStatus().isS_JOIN_ENABLE();
        boolean sLeaveEnabled = ConfigHandler.cached().getStatus().isS_LEAVE_ENABLE();
        boolean sDeathEnabled = ConfigHandler.cached().getStatus().isS_DEATH_ENABLE();
        boolean sAchievementsEnabled = ConfigHandler.cached().getStatus().isS_ADVANCE_ENABLE();
        boolean sWelcomeEnabled = ConfigHandler.cached().getStatus().isS_QQ_WELCOME_ENABLE();

        var groupId = ConfigHandler.cached().getCommon().getGroupIdList().toString();
        boolean debuggable = ConfigHandler.cached().getCommon().isDebuggable();
        boolean connected = BotApi.service != null;
        boolean white = BotApi.SERVER.getPlayerList().isUsingWhitelist();
        String host = ConfigHandler.cached().getBotConfig().getUrl();
        long QQid = ConfigHandler.cached().getCommon().getBotId();
        String toSend =
                "\n姬妻人服务状态:\n"
                        + "姬妻人QQId:" + QQid + " \n"
                        + "框架服务器:" + host + " \n"
                        + "WebSocket连接状态:" + connected + "\n"
                        + "互通的群号:" + groupId + "\n"
                        + "全局服务状态:" + clientEnabled + "\n"
                        + "开发者模式状态:" + debuggable + "\n"
                        + "白名单是否开启:" + white + "\n"
                        + "*************************************\n"
                        + "全局接收消息状态:" + receiveEnabled + "\n"
                        + "接收QQ群聊天消息状态:" + rChatEnabled + "\n"
                        + "接收QQ群命令消息状态:" + rCmdEnabled + "\n"
                        + "*************************************\n"
                        + "全局发送消息状态:" + sendEnabled + "\n"
                        + "发送玩家加入消息状态:" + sJoinEnabled + "\n"
                        + "发送玩家离开消息状态:" + sLeaveEnabled + "\n"
                        + "发送玩家死亡消息状态:" + sDeathEnabled + "\n"
                        + "发送玩家成就消息状态:" + sAchievementsEnabled + "\n"
                        + "发送群成员进/退群消息状态:" + sWelcomeEnabled + "\n";
        context.getSource().sendSuccess(Component.literal(toSend), true);
        ConfigHandler.save();
        return Command.SINGLE_SUCCESS;
    }
}
