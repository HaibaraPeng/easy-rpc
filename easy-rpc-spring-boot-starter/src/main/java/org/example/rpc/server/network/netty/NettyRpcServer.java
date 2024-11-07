package org.example.rpc.server.network.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.server.network.RequestHandler;
import org.example.rpc.server.network.RpcServer;

/**
 * @Author Roc
 * @Date 2024/11/4 15:28
 */
@Slf4j
public class NettyRpcServer implements RpcServer {

    private int port;
    private RequestHandler requestHandler;
    private Channel channel;

    public NettyRpcServer(int port, RequestHandler requestHandler) {
        this.port = port;
        this.requestHandler = requestHandler;
    }

    @Override
    public void start() {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务器端启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    // 设置两个线程组
                    .group(bossGroup, workerGroup)
                    // 设置服务端通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置bossGroup的线程队列大小
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置初始化器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ChannelRequestHandler());
                        }
                    });
            // 绑定端口号，同步启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("Server start success, port: {}", port);
            channel = channelFuture.channel();
            // 等待服务端口关闭
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("Server start error", e);
        } finally {
            // 释放线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        channel.close();
    }

    private class ChannelRequestHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("channel active: {}", ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("Server receive a message: {}", msg);
            final ByteBuf byteBuf = (ByteBuf) msg;
            final byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data);
            // 调用请求处理器开始处理客户端请求
            final byte[] respBytes = requestHandler.handleRequest(data);
            log.info("Server send response message: {}", respBytes);
            final ByteBuf respBuf = Unpooled.buffer(respBytes.length);
            respBuf.writeBytes(respBytes);
            ctx.writeAndFlush(respBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.warn("exception caught", cause);
            ctx.close();
        }
    }
}
