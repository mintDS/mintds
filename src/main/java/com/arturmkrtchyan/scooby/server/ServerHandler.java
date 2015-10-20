package com.arturmkrtchyan.scooby.server;

import com.arturmkrtchyan.scooby.core.KeyValueStoreHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    KeyValueStoreHandler storeHandler = new KeyValueStoreHandler();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        ctx.write(msg);
        System.out.println(msg);
        storeHandler.handle(null);
        if("close".equals(msg.trim())) {
            ctx.write("bye.");
            ctx.flush();
            ctx.close();
        }
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
