package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.core.KeyValueStoreRouter;
import com.arturmkrtchyan.mintds.protocol.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private final KeyValueStoreRouter storeRouter = KeyValueStoreRouter.getInstance();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) {
        ctx.write("OK!" + System.lineSeparator());
        System.out.println(msg);
        storeRouter.handle(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
