package org.example.rpc.client.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author Roc
 * @Date 2024/11/4 14:48
 */
@Slf4j
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {

    private byte[] data;
    private byte[] response;
    private CountDownLatch countDownLatch;

    public ClientChannelHandler(byte[] data) {
        this.data = data;
        this.countDownLatch = new CountDownLatch(1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ByteBuf buf = Unpooled.buffer(data.length);
        buf.writeBytes(data);
        log.info("client send message: {}", buf);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client receive message: {}", msg);
        ByteBuf buf = (ByteBuf) msg;
        response = new byte[buf.readableBytes()];
        buf.readBytes(response);
        countDownLatch.countDown();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    byte[] response() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
