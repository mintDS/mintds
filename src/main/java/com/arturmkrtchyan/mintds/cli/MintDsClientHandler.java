package com.arturmkrtchyan.mintds.cli;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Sharable
public class MintDsClientHandler extends SimpleChannelInboundHandler<String> {

    private final static Logger logger = LoggerFactory.getLogger(MintDsClientHandler.class);


    private final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        queue.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Closing connection due to exception.", cause);
        ctx.close();
    }

    public BlockingQueue<String> queue() {
        return queue;
    }
}
