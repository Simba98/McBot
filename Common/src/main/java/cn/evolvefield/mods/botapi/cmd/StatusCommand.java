package cn.evolvefield.mods.botapi.cmd;


import cn.evolvefield.mods.botapi.CommonBotApi;
import cn.evolvefield.mods.botapi.Constants;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class StatusCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean clientEnabled = Constants.config.getCommon().isEnable();

        boolean receiveEnabled = Constants.config.getStatus().isRECEIVE_ENABLED();
        boolean rChatEnabled = Constants.config.getStatus().isR_CHAT_ENABLE();
        boolean rCmdEnabled = Constants.config.getStatus().isR_COMMAND_ENABLED();

        boolean sendEnabled = Constants.config.getStatus().isSEND_ENABLED();
        boolean sJoinEnabled = Constants.config.getStatus().isS_JOIN_ENABLE();
        boolean sLeaveEnabled = Constants.config.getStatus().isS_LEAVE_ENABLE();
        boolean sDeathEnabled = Constants.config.getStatus().isS_DEATH_ENABLE();
        boolean sAchievementsEnabled = Constants.config.getStatus().isS_ADVANCE_ENABLE();
        boolean sWelcomeEnabled = Constants.config.getStatus().isS_WELCOME_ENABLE();

        var groupId = Constants.config.getCommon().getGroupIdList().toString();
        boolean debuggable = Constants.config.getCommon().isDebuggable();
        boolean connected = CommonBotApi.service != null;
        boolean white = Constants.SERVER.getPlayerList().isUsingWhitelist();
        String host = Constants.config.getBotConfig().getUrl();
        long QQid = Constants.config.getCommon().getBotId();
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
        return 1;
    }
}
