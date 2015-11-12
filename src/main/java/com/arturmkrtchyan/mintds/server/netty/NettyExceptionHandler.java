package com.arturmkrtchyan.mintds.server.netty;

import com.arturmkrtchyan.mintds.protocol.response.FailureResponse;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
public class NettyExceptionHandler extends ChannelHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(NettyExceptionHandler.class);

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        logger.error("Exception is thrown in the pipeline.", cause);
        ctx.write(new FailureResponse(cause.getCause().getMessage()));
    }
}
