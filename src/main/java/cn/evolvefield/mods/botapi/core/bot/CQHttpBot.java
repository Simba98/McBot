package cn.evolvefield.mods.botapi.core.bot;

import cn.evolvefield.mods.botapi.api.events.*;
import cn.evolvefield.mods.botapi.util.json.JSONObject;
import net.minecraftforge.common.MinecraftForge;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:52
 * Version: 1.0
 */
public class CQHttpBot {
    private String Json;//消息原本文本
    private String message_type;//消息类型

    private long self_id;//机器人qq
    private String message_id;//消息ID、被撤回的消息ID

    private String guild_id;//频道ID

    private String channel_id;//子频道ID

    private String tiny_id;//消息发送者ID

    private String self_tiny_id;//机器人者ID

    private String raw_message;//收到消息
    private long group_id;//消息群号
    private String nickname;//发送人昵称
    private String role;//发送人角色
    private long user_id;//发送人qq、撤回消息qq、事件触发qq
    /**
     * 消息子类型,
     * message类型--normal、anonymous、notice--正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     * notice类型--group_increase--approve、invite 分别表示管理员已同意入群、管理员邀请入群
     * notice类型--group_ban--ban、lift_ban 分别表示禁言、解除禁言
     */
    private String sub_type;//事件子类型

    private String post_type;//上报类型
    private String notice_type;//通知类型
    private long operator_id;//事件操作者QQ
    private String duration;//被禁言时长
    private String file_name;//上传文件名字
    private long file_size;//上传文件大小
    private String target_id;//被戳、运气王QQ

    private String request_type;//请求类型
    private String comment;//验证消息
    private String flag;//请求flag

    private long temp_source;//临时消息来源
    private String font;//字体

    public CQHttpBot(String jsonStr, JSONObject json) {

        this.Json = jsonStr;//消息原本json文本

        if (Json.contains("post_type")) {
            if (json.getString("post_type").equalsIgnoreCase("meta_event")) {
                return;
            }
            post_type = json.getString("post_type");
        } else {
            post_type = null;
        }
        if (Json.contains("message_type")) {
            message_type = json.getString("message_type");
        } else {
            message_type = null;
        }

        if (message_type != null) {
            //群聊事件
            if (message_type.equalsIgnoreCase("group")) {
                this.self_id = json.getLong("self_id");//机器人qq
                this.raw_message = json.getString("raw_message");//收到消息
                this.group_id = json.getLong("group_id");//消息群号
                this.nickname = json.getJSONObject("sender").getString("nickname");//发送人昵称
                this.role = json.getJSONObject("sender").getString("role");//发送人角色
                this.user_id = json.getLong("user_id");//发送人qq
                this.sub_type = json.getString("sub_type");//事件子类型


                GroupMessageEvent event = new GroupMessageEvent(Json, self_id, raw_message, group_id, nickname, role, user_id, sub_type);
                MinecraftForge.EVENT_BUS.post(event);

            }

            //私聊事件
            if (message_type.equalsIgnoreCase("private")) {
                this.self_id = json.getLong("self_id");//机器人qq
                this.raw_message = json.getString("raw_message");//收到消息
                this.nickname = json.getJSONObject("sender").getString("nickname");//发送人昵称
                this.user_id = json.getLong("user_id");//发送人qq
                this.sub_type = json.getString("sub_type");//事件子类型

                if (Json.contains("group_id")) {
                    this.temp_source = json.getJSONObject("sender").getLong("group_id");//临时消息来源
                } else {
                    this.temp_source = 0;
                }


                PrivateMessageEvent event = new PrivateMessageEvent(Json, self_id, raw_message, nickname, user_id, sub_type, temp_source);
                MinecraftForge.EVENT_BUS.post(event);
            }

            //频道消息支持
            if (message_type.equalsIgnoreCase("guild")) {
                this.sub_type = json.getString("sub_type");//事件子类型
                this.guild_id = json.getString("guild_id");
                this.channel_id = json.getString("channel_id");
                this.tiny_id = json.optJSONObject("sender").optString("tiny_id");
                this.nickname = json.optJSONObject("sender").optString("nickname");//发送人昵称
                this.self_tiny_id = json.getString("self_tiny_id");

                this.raw_message = json.getString("message");//收到消息

                ChannelGroupMessageEvent event = new ChannelGroupMessageEvent(Json, sub_type, guild_id, channel_id, self_tiny_id, tiny_id, nickname, raw_message);
                MinecraftForge.EVENT_BUS.post(event);
            }

        }


        if (post_type != null) {
            //通知事件
            if (post_type.equalsIgnoreCase("notice")) {
                this.notice_type = json.getString("notice_type");//通知类型

                if (Json.contains("self_id")) {
                    this.self_id = json.getLong("self_id");//机器人qq
                } else {
                    this.self_id = 0;
                }
                if (Json.contains("notice_type")) {
                    this.notice_type = json.getString("notice_type");//通知类型
                } else {
                    this.notice_type = null;
                }
                if (Json.contains("sub_type")) {
                    this.sub_type = json.getString("sub_type");//事件子类型
                } else {
                    this.sub_type = null;
                }
                if (Json.contains("group_id")) {
                    this.group_id = json.getLong("group_id");//机器人qq
                } else {
                    this.group_id = 0;
                }
                if (Json.contains("user_id")) {
                    this.user_id = json.getLong("user_id");//发送人qq、撤回消息qq、事件触发qq
                } else {
                    this.user_id = 0;
                }
                if (Json.contains("operator_id")) {
                    this.operator_id = json.getLong("operator_id");//操作者QQ
                } else {
                    this.operator_id = 0;
                }
                if (Json.contains("duration")) {
                    this.duration = json.getString("duration");//被禁言时长
                } else {
                    this.duration = null;
                }
                if (Json.contains("file")) {//上传文件数据
                    this.file_name = json.getJSONObject("file").getString("name");//上传文件名字
                    this.file_size = json.getJSONObject("file").getLong("size");//上传文件大小
                } else {
                    this.file_name = null;
                    this.file_size = 0;
                }
                if (Json.contains("target_id")) {//被戳、运气王QQ
                    this.target_id = json.getString("target_id");
                } else {
                    this.target_id = null;
                }


                NoticeEvent event = new NoticeEvent(Json, self_id, sub_type, notice_type, group_id, user_id, operator_id);
                MinecraftForge.EVENT_BUS.post(event);
            }

            //请求事件|加群、邀请机器人加群、加机器人好友
            if (post_type.equalsIgnoreCase("request")) {
                this.self_id = json.optLong("self_id");//机器人qq
                this.user_id = json.optLong("user_id");//发送请求的qq
                this.request_type = json.optString("request_type");//请求类型
                this.comment = json.optString("comment");//验证消息
                this.flag = json.optString("flag");//请求flag
                this.group_id = json.optString("group_id");//请求群号
                this.sub_type = json.optString("sub_type");//请求子类型


                RequestEvent event = new RequestEvent(Json, self_id, user_id, request_type, comment, flag, group_id, sub_type);
                MinecraftForge.EVENT_BUS.post(event);
            }
        }
    }
}
