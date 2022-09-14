package cn.evolvefield.mods.botapi.model.event;

import cn.evolvefield.mods.botapi.model.event.global.Message;
import cn.evolvefield.mods.botapi.model.event.message.GroupMessage;
import cn.evolvefield.mods.botapi.model.event.message.PrivateMessage;
import cn.evolvefield.mods.botapi.model.event.notice.*;
import cn.evolvefield.mods.botapi.model.event.request.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 17:04
 * Version: 1.0
 */
public class MessageMap {
    public static Map<String, Class<? extends Message>> messageMap = new HashMap<>();

    static {
        messageMap.put("groupMessage", GroupMessage.class);
        messageMap.put("privateMessage", PrivateMessage.class);

        messageMap.put("friend", FriendRequest.class);
        messageMap.put("group", GroupRequest.class);

        messageMap.put("group_upload", GroupFileUploadNotice.class);
        messageMap.put("group_admin", GroupAdminNotice.class);
        messageMap.put("group_decrease", GroupDecreaseNotice.class);
        messageMap.put("group_increase", GroupIncreaseNotice.class);
        messageMap.put("group_ban", GroupBanNotice.class);
        messageMap.put("group_recall", GroupRecallNotice.class);
        messageMap.put("group_poke", GroupPokeNotice.class);
        messageMap.put("lucky_king", GroupLuckyKingNotice.class);
        messageMap.put("honor", GroupHonorNotice.class);
        messageMap.put("group_card", GroupCardNotice.class);

        messageMap.put("friend_add", FriendAddNotice.class);
        messageMap.put("friend_recall", FriendRecallNotice.class);
        messageMap.put("friend_poke", FriendPokeNotice.class);
        messageMap.put("essence", EssenceNotice.class);
    }
}
