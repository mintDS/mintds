package com.arturmkrtchyan.mintds.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MintDsClientHandler extends SimpleChannelInboundHandler<String> {

    private final static Logger logger = LoggerFactory.getLogger(MintDsClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(Thread.currentThread().getName().equals("nioEventLoopGroup-2-1")) {
            System.out.println(ctx.channel().toString() + Thread.currentThread().getName() + " " + msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Closing connection due to exception.", cause);
        ctx.close();
    }

}
