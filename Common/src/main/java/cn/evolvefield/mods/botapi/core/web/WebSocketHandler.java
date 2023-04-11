package cn.evolvefield.mods.botapi.core.web;

import cn.evolvefield.mods.botapi.core.action.Request;
import cn.evolvefield.mods.botapi.handler.EventHandler;
import com.google.gson.JsonSyntaxException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.server.MinecraftServer;

import static cn.evolvefield.mods.botapi.core.web.WebSocketServer.gson;
import static cn.evolvefield.mods.botapi.core.web.WebSocketServer.log;

/**
 * Project: Bot-Connect-koishi
 * Author: cnlimiter
 * Date: 2023/4/9 23:15
 * Description:
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final DefaultChannelGroup clients;

    private final MinecraftServer server;
    public WebSocketHandler(DefaultChannelGroup clients, MinecraftServer server) {
        this.clients = clients;
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.info("▌ §c收到客户端:{} 消息 {} ", ctx.channel().id().asShortText(), msg.text());
        try {
            Request request = gson.fromJson(msg.text(), Request.class);
            var event = new EventHandler(server, request);
            event.run();
        } catch (JsonSyntaxException e) {
            log.error("Json语法错误:{}", msg.text());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        clients.add(ctx.channel());
        log.info("▌ §c客户端:{} 已连接 §a┈━═☆", ctx.channel().id().asShortText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("▌ §c客户端:{} 断开连接 §a┈━═☆", ctx.channel().id().asShortText());
    }
}
