package com.arturmkrtchyan.mintds.cli;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Sharable
public class ClientHandler  extends SimpleChannelInboundHandler<String> {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        queue.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public BlockingQueue<String> queue() {
        return queue;
    }
}
