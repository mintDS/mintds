package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.core.KeyValueStoreRouter;
import com.arturmkrtchyan.mintds.protocol.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private final KeyValueStoreRouter storeRouter = new KeyValueStoreRouter();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) {
        ctx.write("OK!" + System.lineSeparator());
        logger.debug("Received: " + msg.toString());
        storeRouter.handle(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.error("Closing connection due to exception.", cause);
        cause.printStackTrace();
        ctx.close();
    }

}
