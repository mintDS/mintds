package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.core.KeyValueStoreRouter;
import com.arturmkrtchyan.mintds.protocol.request.Request;
import com.arturmkrtchyan.mintds.protocol.response.Response;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private final KeyValueStoreRouter storeRouter = new KeyValueStoreRouter();

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final Request msg) {
        logger.debug("Received: " + msg.toString());
        final Response response = storeRouter.handle(msg);
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        // Close the connection when an exception is raised.
        logger.error("Closing connection due to exception.", cause);
        cause.printStackTrace();
        ctx.close();
    }

}
