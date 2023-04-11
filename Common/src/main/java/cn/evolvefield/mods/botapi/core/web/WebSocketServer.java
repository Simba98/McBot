package cn.evolvefield.mods.botapi.core.web;

import cn.evolvefield.mods.botapi.core.entity.UserMessage;
import cn.evolvefield.mods.botapi.init.config.BotConfig;
import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project: Bot-Connect-koishi
 * Author: cnlimiter
 * Date: 2023/4/9 23:13
 * Description:
 */
public class WebSocketServer {
    public static final Logger log = LoggerFactory.getLogger("BotServer");
    public static final Gson gson = new Gson();
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private ChannelFuture future;

    /**
     * 用于记录和管理所有客户端的channel
     */
    private final DefaultChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void create(BotConfig config, MinecraftServer server) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(@NotNull SocketChannel ch) {
                        // HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
                        ch.pipeline().addLast(new HttpServerCodec());
                        //分块向客户端写数据，防止发送大文件时导致内存溢出， channel.write(new ChunkedFile(new File("bigFile.mkv")))
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                        // 将HttpMessage和HttpContents聚合到一个完成的 FullHttpRequest或FullHttpResponse中
                        // 具体是FullHttpRequest对象还是FullHttpResponse对象取决于是请求还是响应
                        // 需要放到HttpServerCodec这个处理器后面

                        //聚合器,使用websocket会用到
                        ch.pipeline().addLast(new HttpObjectAggregator(1024 * 64));
                        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
                        ch.pipeline().addLast(new WebSocketServerCompressionHandler());
                        // WebSocket 握手、控制帧处理
                        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/" + config.getPath(), null, true));
                        // WebSocket 消息处理
                        ch.pipeline().addLast(new WebSocketHandler(clients, server));
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        super.exceptionCaught(ctx, cause);
                        ctx.channel().parent().close();
                    }
                });
        try {
            // 绑定端口
            future = bootstrap.bind(config.getHost(), config.getPort()).sync();
            log.info("▌ §c服务端: {}:{} 已开启§a┈━═☆", config.getHost(), config.getPort());
            // 一直阻塞直到服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            stop();
            log.error(e.getLocalizedMessage());
        } finally {
            stop();
        }

    }

    /**
     * 停止服务器
     */
    public void stop() {
        clients.forEach(channel -> {
            try {
                channel.disconnect().await(500);
                channel.close().await(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        future.channel().close();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    /**
     * 广播用户消息给所有客户端
     *
     * @param msg 用户消息
     */
    public void broadcast(UserMessage msg) {
        clients.writeAndFlush(new TextWebSocketFrame(gson.toJson(msg)));
    }
}
